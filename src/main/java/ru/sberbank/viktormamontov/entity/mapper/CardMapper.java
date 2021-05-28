package ru.sberbank.viktormamontov.entity.mapper;

import ru.sberbank.viktormamontov.dao.AccountDao;
import ru.sberbank.viktormamontov.dao.AccountDaoImpl;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.entity.Card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CardMapper {

    public static Card getCardFromResultSet(ResultSet rs) {
        Card card = null;
        try {
            long id = rs.getLong("id");
            String number = rs.getString("number");
            LocalDate expiration = rs.getDate("expiration").toLocalDate();
            String cvv = rs.getString("cvv");
            Card.Status status = Card.Status.valueOf(rs.getString("status"));
            long accountId = rs.getLong("account_id");

            AccountDao accountDao = AccountDaoImpl.getInstance();
            Account account = accountDao.getById(accountId);

            card = new Card(id,number, expiration, cvv, status, account);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return card;
    }
}
