package ru.sberbank.viktormamontov.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.viktormamontov.DbUtil;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.entity.Card;
import ru.sberbank.viktormamontov.entity.Client;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDaoImplTest {

    private CardDao cardDao = CardDaoImpl.getInstance();
    Client client;
    Account account;
    Card card;

    @BeforeEach
    void setUp() {
        DbUtil.createAndFillTables();
        client = new Client(1, "Ivan", "Ivanov");
        account = new Account(1, "55555444445555522222", 2000.1, client);
        card = new Card
                (1, "1111 3333 2222 8888", LocalDate.of(2022, 3, 1), "321", Card.Status.ACTIVE, account);
    }

    @Test
    void getById() throws SQLException {
        Card byId = cardDao.getById(1);
        assertEquals(card, byId);
    }

    @Test
    void getByNumber() throws SQLException {
        Card byNumber = cardDao.getByNumber("1111 3333 2222 8888");
        assertEquals(card, byNumber);
    }

    @Test
    void add() throws SQLException {
        Card newCard = new Card
                (1, "1112 3333 2222 8888", LocalDate.of(2022, 4, 1), "421", Card.Status.ACTIVE, account);

        List<Card> cardsByAccountId = cardDao.getCardsByAccountId(1);
        assertTrue(!cardsByAccountId.contains(newCard));

        cardDao.add(newCard);

        cardsByAccountId = cardDao.getCardsByAccountId(1);
        assertTrue(cardsByAccountId.contains(newCard));
    }

    @Test
    void getCardsByAccountId() throws SQLException {
        List<Card> cardsByAccountId = cardDao.getCardsByAccountId(1);
        assertTrue(cardsByAccountId.contains(card));
    }
}