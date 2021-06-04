package ru.sberbank.viktormamontov.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.viktormamontov.DbUtil;
import ru.sberbank.viktormamontov.entity.Client;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ClientDaoImplTest {

    private ClientDao clientDao = ClientDaoImpl.getInstance();

    @BeforeEach
    void setUp() {
        DbUtil.createAndFillTables();
    }


    @Test
    void getById() throws SQLException {
        Client byId = clientDao.getById(1);
        assertEquals(new Client(1, "Ivan", "Ivanov"), byId);
    }
}