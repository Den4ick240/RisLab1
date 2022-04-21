package ru.nsu.zhigalov.ris.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() throws IOException, SQLException {
        if (connection == null)
            connection = createConnection();
        return connection;
    }


    private static Connection createConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(DatabaseConnection.class.getResourceAsStream("/db.properties"));
        String url = properties.getProperty("URL");
        String login = properties.getProperty("LOGIN");
        String password = properties.getProperty("PASSWORD");
        connection = DriverManager.getConnection(url, login, password);
        connection.setAutoCommit(false);
        return connection;
    }
}
