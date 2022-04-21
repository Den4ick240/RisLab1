package ru.nsu.zhigalov.ris.db;

import org.postgresql.util.PSQLException;
import ru.nsu.zhigalov.ris.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.stream.Collectors;

public class DatabaseController implements AutoCloseable {
    private static final String CLEAN_POSTFIX = "_clean.sql";
    private static final String CREATE_POSTFIX = "_create.sql";
    private static final String PREFIX = "sqlScripts/";
    private final Connection connection;
    private final String[] scriptNames;

    private Connection createConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(DatabaseController.class.getResourceAsStream("/db.properties"));
        String url = properties.getProperty("URL");
        String login = properties.getProperty("LOGIN");
        String password = properties.getProperty("PASSWORD");
        var connection = DriverManager.getConnection(url, login, password);
        connection.setAutoCommit(false);
        return connection;
    }

    public DatabaseController(String[] scriptNames) throws SQLException, IOException {
        connection = createConnection();
        this.scriptNames = scriptNames;
    }

    public void cleanDatabase() throws IOException, SQLException {
        for (int i = scriptNames.length - 1; i >= 0; i--)
            try {
                cleanScript(scriptNames[i]);
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();

            }
    }

    public void createDatabase() throws SQLException, IOException {
        for (var script : scriptNames)
            createScript(script);
    }

    private void cleanScript(String script) throws SQLException, IOException {
        String resourcePath = PREFIX + script + CLEAN_POSTFIX;
        runScript(resourcePath);
    }

    private void createScript(String script) throws SQLException, IOException {
        String resourcePath = PREFIX + script + CREATE_POSTFIX;
        runScript(resourcePath);
    }

    private void runScript(String resourcePath) throws SQLException, IOException {
        String sql = Utils.getResourceFileAsString(resourcePath);
        connection.prepareStatement(sql).execute();
        connection.commit();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
