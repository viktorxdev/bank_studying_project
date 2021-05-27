package ru.sberbank.viktormamontov;


import java.sql.*;

public class Main {


    public static void main(String[] args) {
        Connection connection = DbConnector.openConnection();

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO test VALUES(?)");) {
//            Statement st = connection.createStatement();
//            st.executeUpdate("insert into test values ('reererere')");
//            st.close();
            statement.setString(1, "without_driver");
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try (Statement st = connection.createStatement();){
            ResultSet resultSet = st.executeQuery("select * from test");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        DbConnector.closeConnection();


    }
}
