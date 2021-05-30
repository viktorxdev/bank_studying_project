package ru.sberbank.viktormamontov;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;


public class DbUtil {

    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:~/bank_db";
    public static final String USER = "user";
    public static final String PASS = "user";

    private static final String CREATING_TABLES_PATH = "/Users/u19223645/Desktop/sql_create_tables.txt";
    private static final String FILLING_TABLES_PATH = "/Users/u19223645/Desktop/sql_insert_data.txt";

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

    private static void doQuery(List<String> lines) {
        StringBuilder queries = new StringBuilder();
        for (String line : lines) {
            queries.append(line);
        }
        String[] queryArr = queries.toString().split(";");

        for (int i = 0; i < queryArr.length; i++) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 PreparedStatement statement = conn.prepareStatement(queryArr[i])) {
                statement.executeUpdate();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }

    }
}
