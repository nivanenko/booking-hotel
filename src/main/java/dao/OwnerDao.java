package dao;

import model.Booking;
import model.Hotel;
import util.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class OwnerDao {

    public OwnerDao() {
        ds = DataSourceUtil.getDataSource();
    }

    private DataSource ds;

    public boolean addHotel(String ownerName, String name, String city, String address, String desc) {
        try (Connection conn = ds.getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT OWNER_EMAIL FROM OWNERS WHERE OWNER_EMAIL = ?")) {
                ps.setString(1, ownerName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return false;
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO HOTELS" +
                            " (HOTEL_ID, HOTEL_DESCRIPTION, HOTEL_NAME, HOTEL_ADDRESS, HOTEL_OWNER, HOTEL_CITY)" +
                            " VALUES (HOTEL_SEQ.nextval, ?, ?, ?, ?, ?)")) {
                ps.setString(1, desc);
                ps.setString(2, name);
                ps.setString(3, address);
                ps.setString(4, ownerName);
                ps.setString(5, city);
                ps.executeUpdate();
            }


        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                return false;
            }
            e.printStackTrace();
        }
        return true;
    }

    public ArrayList<Hotel> retrieveHotels(String ownerName) {
        ArrayList<Hotel> hotels = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT HOTELS.HOTEL_NAME, " +
                            "HOTELS.HOTEL_DESCRIPTION," +
                            "HOTELS.HOTEL_CITY, " +
                            "COUNT(HOTELS.HOTEL_ID) AS ROOMS_QUANTITY " +
                            "FROM ROOMS " +
                            "INNER JOIN HOTELS " +
                            "ON HOTELS.HOTEL_ID = ROOMS.ROOM_HOTEL " +
                            "INNER JOIN OWNERS " +
                            "ON OWNERS.OWNER_EMAIL = HOTELS.HOTEL_OWNER " +
                            "WHERE HOTELS.HOTEL_OWNER = ? " +
                            "GROUP BY HOTELS.HOTEL_NAME, " +
                            "HOTELS.HOTEL_DESCRIPTION," +
                            "HOTELS.HOTEL_CITY")) {
                ps.setString(1, ownerName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }
                    do {
                        Hotel hotel = new Hotel();
                        hotel.setHotelName(rs.getString("HOTEL_NAME"));
                        hotel.setHotelDescription(rs.getString("HOTEL_DESCRIPTION"));
                        hotel.setCity(rs.getString("HOTEL_CITY"));
                        hotel.setRoomsQuantity(rs.getInt("ROOMS_QUANTITY"));
                        hotels.add(hotel);
                    } while (rs.next());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public boolean addRoom(String ownerName, String hotelName, String roomDesc, int capacity, int price) {
        int hotelId;
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT OWNER_EMAIL FROM OWNERS WHERE OWNER_EMAIL = ?")) {
                ps.setString(1, ownerName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return false;
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT HOTELS.HOTEL_ID " +
                    "FROM HOTELS " +
                    "WHERE HOTELS.HOTEL_NAME = ? " +
                    "AND HOTELS.HOTEL_OWNER = ?")) {
                ps.setString(1, hotelName);
                ps.setString(2, ownerName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return false;
                    }
                    do {
                        hotelId = rs.getInt("HOTEL_ID");
                    } while (rs.next());
                }
            }


            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO ROOMS " +
                            "(ROOM_ID, ROOM_CAPACITY, ROOM_DESCRIPTION, ROOM_HOTEL, ROOM_PRICE) " +
                            "VALUES (ROOM_SEQ.nextval, ?, ?, ?, ?)")) {
                ps.setInt(1, capacity);
                ps.setString(2, roomDesc);
                ps.setInt(3, hotelId);
                ps.setInt(4, price);
                ps.executeUpdate();
            }


        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                return false;
            }
            e.printStackTrace();
        }
        return true;
    }

    public ArrayList<Booking> retrieveBookings(String ownerName) {
        ArrayList<Booking> bookings = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT HOTELS.HOTEL_NAME, " +
                            "BOOKINGS.BOOKING_ROOM, " +
                            "BOOKINGS.BOOKING_DATE_IN, " +
                            "BOOKINGS.BOOKING_DATE_OUT, " +
                            "ROOMS.ROOM_PRICE " +
                            "FROM ROOMS " +
                            "INNER JOIN HOTELS " +
                            "ON ROOMS.ROOM_HOTEL = HOTELS.HOTEL_ID " +
                            "INNER JOIN BOOKINGS " +
                            "ON ROOMS.ROOM_ID = BOOKINGS.BOOKING_ROOM " +
                            "INNER JOIN OWNERS " +
                            "ON HOTELS.HOTEL_OWNER = OWNERS.OWNER_EMAIL " +
                            "WHERE HOTELS.HOTEL_OWNER = ? " +
                            "GROUP BY HOTELS.HOTEL_NAME, " +
                            "BOOKINGS.BOOKING_ROOM, " +
                            "BOOKINGS.BOOKING_DATE_IN, " +
                            "BOOKING_DATE_OUT, " +
                            "ROOMS.ROOM_PRICE"
            )) {
                ps.setString(1, ownerName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }
                    do {
                        Booking booking = new Booking();

                        booking.setHotelName(rs.getString("HOTEL_NAME"));
                        booking.setRoomId(rs.getInt("BOOKING_ROOM"));
                        booking.setDateIn(rs.getDate("BOOKING_DATE_IN"));
                        booking.setDateOut(rs.getDate("BOOKING_DATE_OUT"));
                        booking.setPrice(rs.getInt("ROOM_PRICE"));
                        bookings.add(booking);
                    } while (rs.next());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }
}
