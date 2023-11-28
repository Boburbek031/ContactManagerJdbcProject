package uz.ali;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/jbdc_lesson_database",
                    "db_user_of_jdbc_lesson", "jdbc0661");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
