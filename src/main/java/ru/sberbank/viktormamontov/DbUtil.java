package ru.sberbank.viktormamontov;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.Properties;


public class DbUtil {

    private static Properties props = loadProperties();

    public static final String JDBC_DRIVER = props.getProperty("datasource.driver");
    public static final String URL = props.getProperty("datasource.url");
    public static final String USER = props.getProperty("datasource.username");
    public static final String PASS = props.getProperty("datasource.password");

    private static final String CREATING_TABLES_PATH = props.getProperty("path.creating");
    private static final String FILLING_TABLES_PATH = props.getProperty("path.filling");

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void createAndFillTables() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(CREATING_TABLES_PATH));
            doQuery(lines);

            lines = Files.readAllLines(Paths.get(FILLING_TABLES_PATH));
            doQuery(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("database.properties")){
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static void doQuery(List<String> lines) {
        StringBuilder queries = new StringBuilder();
        for (String line : lines) {
            queries.append(line);
        }
        String[] queryArr = queries.toString().split(";");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)){
            for (int i = 0; i < queryArr.length; i++) {
                try (PreparedStatement statement = conn.prepareStatement(queryArr[i])) {
                    statement.executeUpdate();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
