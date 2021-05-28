package ru.sberbank.viktormamontov;


import ru.sberbank.viktormamontov.dao.*;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.entity.Card;
import ru.sberbank.viktormamontov.entity.Client;


public class Main {

    public static void main(String[] args) {
//        ClientDao clientDao = ClientDaoImpl.getInstance();
//        Client byId = clientDao.getById(1l);
//        System.out.println(byId);

//        AccountDao accountDao = AccountDaoImpl.getInstance();
//        Account byId = accountDao.getById(1);
//        System.out.println(byId);

        CardDao cardDao = CardDaoImpl.getInstance();
        Card byId = cardDao.getById(1);
        System.out.println(byId);


//        try (Connection connection = DriverManager.getConnection(DbUtil.DB_URL, DbUtil.USER, DbUtil.PASS);) {
//
//            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO test VALUES(?)");) {
//                statement.setString(1, "ififif");
//                statement.executeUpdate();
//            } catch (SQLException throwable) {
//                throwable.printStackTrace();
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
