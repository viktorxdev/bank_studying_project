package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.entity.Card;

import java.sql.SQLException;
import java.util.List;

public interface CardDao {

    Card getById(long id) throws SQLException;

    Card getByNumber(String number) throws SQLException;

    void add(Card card) throws SQLException;

    List<Card> getCardsByAccountId(long accountId) throws SQLException;
}
