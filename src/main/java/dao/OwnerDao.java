package dao;

import util.DataSourceUtil;

import java.sql.*;

public class OwnerDao {
    public boolean addHotel(String ownerName, String name, String city, String address, String desc) {
        try (Connection conn = DataSourceUtil.getDataSource().getConnection()) {

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
}
