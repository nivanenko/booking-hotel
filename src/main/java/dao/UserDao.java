package dao;

import util.DataSourceUtil;

import java.sql.*;

public class UserDao {
    public boolean checkUser(String login, String password) {
        try (Connection conn = DataSourceUtil.getDataSource().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT CUST_PASS FROM Customers WHERE CUST_EMAIL = ?")) {
                ps.setString(1, login);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return false;
                    }
                    do {
                        return password.equals(rs.getString("cust_pass"));
                    } while (rs.next());
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerUser(String login, String name, String password, boolean isOwner) {
        try (Connection conn = DataSourceUtil.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO CUSTOMERS" +
                            " (CUST_EMAIL, CUST_NAME, CUST_PASS)" +
                            " VALUES (?, ?, ?)")) {
                ps.setString(1, login);
                ps.setString(2, name);
                ps.setString(3, password);
                ps.executeUpdate();
            } catch (SQLException e) {
                conn.rollback();
                conn.setAutoCommit(true);
                e.printStackTrace();
            }

            if (isOwner) {
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO OWNERS" +
                                " (OWNER_EMAIL)" +
                                " VALUES (?)")) {
                    ps.setString(1, login);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    e.printStackTrace();
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                return false;
            }
            e.printStackTrace();
        }
        return true;
    }

    public boolean isOwner(String login) {
        try (Connection conn = DataSourceUtil.getDataSource().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT OWNER_EMAIL FROM OWNERS WHERE OWNER_EMAIL = ?")) {
                ps.setString(1, login);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return false;
                    }
                    do {
                        return login.equals(rs.getString("owner_email"));
                    } while (rs.next());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}