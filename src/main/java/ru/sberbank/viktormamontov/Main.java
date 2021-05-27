package ru.sberbank.viktormamontov;


import ru.sberbank.viktormamontov.dao.ClientDao;
import ru.sberbank.viktormamontov.dao.ClientDaoImpl;
import ru.sberbank.viktormamontov.entity.Client;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        ClientDao clientDao = new ClientDaoImpl();
        Client byId = clientDao.getById(1l);
        System.out.println(byId);


//        try (Connection connection = DriverManager.getConnection(DbUtil.DB_URL, DbUtil.USER, DbUtil.PASS);) {
//
//            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO test VALUES(?)");) {
//                statement.setString(1, "ififif");
//                statement.executeUpdate();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//
//            try (Statement st = connection.createStatement();) {
//                ResultSet resultSet = st.executeQuery("select * from test");
//                while (resultSet.next()) {
//                    System.out.println(resultSet.getString("name"));
//                }
//                resultSet.close();
//            } catch (SQLException throwable) {
//                throwable.printStackTrace();
//            }
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }


    }
}
