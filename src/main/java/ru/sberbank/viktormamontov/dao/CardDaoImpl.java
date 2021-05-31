package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.DbUtil;
import ru.sberbank.viktormamontov.entity.Card;
import ru.sberbank.viktormamontov.dao.mapper.CardMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDaoImpl implements CardDao{

    private static CardDaoImpl instance;
    private CardDaoImpl() {}

    public static CardDaoImpl getInstance() {
        if (instance == null) {
            instance = new CardDaoImpl();
        }
        return instance;
    }

    @Override
    public Card getById(long id) throws SQLException {
        Card card = null;

        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM cards WHERE id=?")){

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            card = CardMapper.getCardFromResultSet(resultSet);

        }
        return card;
    }

    @Override
    public Card getByNumber(String number) throws SQLException {
        Card card = null;

        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM cards WHERE number = ?")){

            statement.setString(1, number);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            card = CardMapper.getCardFromResultSet(resultSet);

        }
        return card;
    }

    @Override
    public void add(Card card) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement =
                conn.prepareStatement("INSERT INTO cards(number, expiration, cvv, status, account_id) VALUES (?,?,?,?,?)")){

            statement.setString(1, card.getNumber());
            statement.setDate(2, Date.valueOf(card.getExpiration()));
            statement.setString(3, card.getCvv());
            statement.setString(4, card.getStatus().toString());
            statement.setLong(5, card.getAccount().getId());

            statement.executeUpdate();

        }

        Card cardWithId = getByNumber(card.getNumber());
        card.setId(cardWithId.getId());
    }

    @Override
    public List<Card> getCardsByAccountId(long accountId) throws SQLException {
        List<Card> cards = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM cards WHERE account_id =?")){

            statement.setLong(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Card card = CardMapper.getCardFromResultSet(resultSet);
                cards.add(card);
            }

        }
        return cards;
    }
}
