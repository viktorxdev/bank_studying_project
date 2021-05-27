package ru.sberbank.viktormamontov;

import java.sql.*;

public class Main {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/bank_db";
    private static final String USER = "user";
    private static final String PASS = "user";

    private static Connection connection;
    static {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO test VALUES(?)");) {
//            Statement st = connection.createStatement();
//            st.executeUpdate("insert into test values ('reererere')");
//            st.close();
            statement.setString(1, "hgftrdtetdy");
            statement.executeUpdate();
//     ????       connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try (Statement st = connection.createStatement();){
            ResultSet resultSet = st.executeQuery("select * from test");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
            resultSet.close();
//      ????      connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
