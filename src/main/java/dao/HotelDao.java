package dao;

import model.Booking;
import model.Hotel;
import model.Room;
import util.CommonUtil;
import util.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class HotelDao {

    public HotelDao() {
        ds = DataSourceUtil.getDataSource();
    }

    private DataSource ds;

    public ArrayList<Hotel> searchHotel(String city, int guests) {
        ArrayList<Hotel> hotels = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT MIN(ROOMS.ROOM_PRICE) AS MIN_ROOM_PRICE, " +
                            "HOTELS.HOTEL_DESCRIPTION, " +
                            "HOTELS.HOTEL_NAME " +
                            "FROM ROOMS " +
                            "INNER JOIN HOTELS " +
                            "ON HOTELS.HOTEL_ID = ROOMS.ROOM_HOTEL " +
                            "WHERE HOTELS.HOTEL_CITY = ?" +
                            "GROUP BY HOTELS.HOTEL_NAME, HOTELS.HOTEL_DESCRIPTION")) {
                ps.setString(1, city);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }
                    do {
                        Hotel hotel = new Hotel();
                        hotel.setHotelName(rs.getString("HOTEL_NAME"));
                        hotel.setHotelDescription(rs.getString("HOTEL_DESCRIPTION"));
                        hotel.setMinPrice(Integer.parseInt(rs.getString("MIN_ROOM_PRICE")));
                        hotels.add(hotel);
                    } while (rs.next());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public ArrayList<Room> retrieveRooms(String hotelName, Date dateIn, Date dateOut) {
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Room> busyRooms = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT ROOMS.ROOM_ID, " +
                            "ROOMS.ROOM_CAPACITY, " +
                            "ROOMS.ROOM_DESCRIPTION, " +
                            "ROOMS.ROOM_PRICE, " +
                            "ROOMS.ROOM_HOTEL " +
                            "FROM ROOMS " +
                            "INNER JOIN HOTELS " +
                            "ON HOTELS.HOTEL_ID = ROOMS.ROOM_HOTEL " +
                            "WHERE HOTELS.HOTEL_NAME = ?")) {
                ps.setString(1, hotelName);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }
                    do {
                        Room room = new Room();
                        room.setId(rs.getInt("ROOM_ID"));
                        room.setCapacity(rs.getInt("ROOM_CAPACITY"));
                        room.setDescription(rs.getString("ROOM_DESCRIPTION"));
                        room.setPrice(rs.getInt("ROOM_PRICE"));
                        room.setHotelId(rs.getInt("ROOM_HOTEL"));
                        rooms.add(room);
                    } while (rs.next());
                }
            }

            for (Room room : rooms) {
                try (PreparedStatement ps = conn.prepareStatement(
                        "SELECT BOOKINGS.BOOKING_DATE_IN, " +
                                "BOOKINGS.BOOKING_DATE_OUT " +
                                "FROM BOOKINGS " +
                                "INNER JOIN ROOMS " +
                                "ON ROOMS.ROOM_ID = BOOKINGS.BOOKING_ROOM " +
                                "WHERE BOOKINGS.BOOKING_ROOM = ?"
                )) {
                    ps.setInt(1, room.getId());
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next() && CommonUtil.isDatesOverLapped(dateIn, dateOut,
                                rs.getDate("BOOKING_DATE_IN"),
                                rs.getDate("BOOKING_DATE_OUT"))) {
                            busyRooms.add(room);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        rooms.removeAll(busyRooms);

        return rooms;
    }

    public boolean createBooking(Room room, Date dateIn, Date dateOut, String customerName) {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO BOOKINGS " +
                            "(BOOKING_ID, BOOKING_DATE_IN, BOOKING_DATE_OUT, BOOKING_ROOM, BOOKING_CUSTOMER) " +
                            "VALUES (BOOK_SEQ.nextval, ?, ?, ?, ?)"
            )) {
                ps.setDate(1, dateIn);
                ps.setDate(2, dateOut);
                ps.setInt(3, room.getId());
                ps.setString(4, customerName);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Booking> retrieveBooking(String customerName) {
        ArrayList<Booking> bookings = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT HOTELS.HOTEL_NAME, " +
                            "BOOKINGS.BOOKING_DATE_IN, " +
                            "BOOKINGS.BOOKING_DATE_OUT, " +
                            "ROOMS.ROOM_PRICE " +
                            "FROM ROOMS " +
                            "INNER JOIN HOTELS " +
                            "ON HOTELS.HOTEL_ID = ROOMS.ROOM_HOTEL " +
                            "INNER JOIN BOOKINGS " +
                            "ON ROOMS.ROOM_ID = BOOKINGS.BOOKING_ROOM " +
                            "WHERE BOOKINGS.BOOKING_CUSTOMER = ?"
            )) {
                ps.setString(1, customerName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }
                    do {
                        Booking booking = new Booking();
                        booking.setHotelName(rs.getString("HOTEL_NAME"));
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

    public ArrayList<Hotel> getAllHotels() {
        ArrayList<Hotel> hotels = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT HOTELS.HOTEL_ADDRESS " +
                            "FROM HOTELS"
            )) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }
                    do {
                        Hotel hotel = new Hotel();
                        hotel.setHotelAddress(rs.getNString("HOTEL_ADDRESS"));
                        hotels.add(hotel);
                    } while (rs.next());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotels;
    }
}

