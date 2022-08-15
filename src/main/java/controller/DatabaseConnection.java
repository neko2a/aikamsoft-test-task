package controller;

import model.Error;
import org.tinylog.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/aikam";
    private static final String DB_Username = "postgres";
    private static final String DB_Password = "postgres";

    private static DatabaseConnection instance;
    private static Connection connection;

    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_Username, DB_Password);
        } catch (SQLException e) {
            Logger.error(new Error(e.getMessage()));
        }
    }

    public static Connection getConnection() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return connection;
    }
}
