package dao;

import model.Hotel;
import model.Room;
import util.DataSourceUtil;

import java.sql.*;
import java.util.ArrayList;

public class HotelDao {
    public ArrayList<Hotel> searchHotel(String city, Date toDate, Date fromDate, int guests) {
        ArrayList<Hotel> hotels = new ArrayList<>();

        try (Connection conn = DataSourceUtil.getDataSource().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT MIN(ROOMS.ROOM_PRICE) AS MIN_ROOM_PRICE, " +
                            "HOTELS.HOTEL_DESCRIPTION, " +
                            "HOTELS.HOTEL_NAME " +
                            "FROM ROOMS " +
                            "INNER JOIN HOTELS " +
                            "ON HOTELS.HOTEL_ID = ROOMS.ROOM_HOTEL " +
                            "INNER JOIN HOTELS " +
                            "ON HOTELS.HOTEL_CITY = ?" +
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

    public ArrayList<Room> retrieveRooms(String hotelName) {
        ArrayList<Room> rooms = new ArrayList<>();

        try (Connection conn = DataSourceUtil.getDataSource().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT ROOMS.ROOM_CAPACITY," +
                            " ROOMS.ROOM_DESCRIPTION," +
                            " ROOMS.ROOM_PRICE " +
                            "FROM ROOMS " +
                            "INNER JOIN HOTELS " +
                            "ON HOTELS.HOTEL_NAME = ?")) {
                ps.setString(1, hotelName);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }
                    do {
                        Room room = new Room();
                        room.setCapacity(rs.getInt("ROOM_CAPACITY"));
                        room.setDescription(rs.getString("ROOM_DESCRIPTION"));
                        room.setPrice(rs.getInt("ROOM_PRICE"));
                        rooms.add(room);
                    } while (rs.next());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}

