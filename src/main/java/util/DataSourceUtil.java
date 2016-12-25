package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceUtil {
    public static DataSource getDataSource() {
        return new HikariDataSource(new HikariConfig("C:\\Users\\Nazar\\IdeaProjects\\booking-hotel\\src\\main\\resources\\db.properties"));
    }
}
