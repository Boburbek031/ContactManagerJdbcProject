package uz.ali.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    public static void createTable() {
        String sqlCreateTableQuery = "create table if not exists contact (" +
                "id serial primary key," +
                "name varchar (25) not null," +
                "surname varchar (25) not null," +
                "phoneNumber varchar (15) not null unique" +
                ");";
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sqlCreateTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/contact_manager_project_in_jdbc_in_java",
                    "contact_manager_user", "Manager0002");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
