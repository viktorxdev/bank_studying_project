package ru.sberbank.viktormamontov.entity.mapper;

import ru.sberbank.viktormamontov.dao.ClientDao;
import ru.sberbank.viktormamontov.dao.ClientDaoImpl;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper {

    public static Account getAccountFromResultSet(ResultSet rs) {
        Account account = null;
        try {
            long id = rs.getLong("id");
            String number = rs.getString("number");
            double balance = rs.getBigDecimal("balance").doubleValue();
            long clientId = rs.getLong("client_id");

            ClientDao clientDao = ClientDaoImpl.getInstance();
            Client client = clientDao.getById(clientId);

            account = new Account(id, number, balance, client);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return account;
    }


}
