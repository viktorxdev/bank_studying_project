package ru.sberbank.viktormamontov.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.viktormamontov.DbUtil;
import ru.sberbank.viktormamontov.entity.Counterparty;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CounterpartyDaoImplTest {

    CounterpartyDao counterpartyDao = CounterpartyDaoImpl.getInstance();
    Counterparty counterparty;

    @BeforeEach
    void setUp() {
        DbUtil.createAndFillTables();
        counterparty = new Counterparty(1, "some company", "some information", 120000.33);
    }

    @Test
    void getById() throws SQLException {
        Counterparty byId = counterpartyDao.getById(1);
        assertEquals(counterparty, byId);
    }

    @Test
    void getByName() throws SQLException {
        Counterparty byName = counterpartyDao.getByName("some company");
        assertEquals(counterparty, byName);
    }

    @Test
    void add() throws SQLException {
        Counterparty newCounterparty = new Counterparty(1, "new name", "new information", 4444.2);

        List<Counterparty> allByClientId = counterpartyDao.getAllByClientId(1);
        assertTrue(!allByClientId.contains(newCounterparty));

        counterpartyDao.add(newCounterparty, 1);

        allByClientId = counterpartyDao.getAllByClientId(1);
        assertTrue(allByClientId.contains(newCounterparty));
    }

    @Test
    void getAllByClientId() throws SQLException {
        List<Counterparty> allByClientId = counterpartyDao.getAllByClientId(1);
        assertTrue(allByClientId.contains(counterparty));
    }

    @Test
    void update() {
    }
}