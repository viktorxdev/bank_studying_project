package ru.sberbank.viktormamontov.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.viktormamontov.DbUtil;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.entity.Client;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoImplTest {

    private final AccountDao accountDao = AccountDaoImpl.getInstance();

    @BeforeEach
    void setUp() {
        DbUtil.createAndFillTables();
    }

    @Test
    void getById() throws SQLException {
        Account byId = accountDao.getById(1);
        assertEquals(new Account(1, "55555444445555522222", 2000.1, new Client(1, "Ivan", "Ivanov")), byId);
    }

    @Test
    void getByNumber() throws SQLException {
        Account byNumber = accountDao.getByNumber("55555444445555522222");
        assertEquals(new Account(1, "55555444445555522222", 2000.1, new Client(1, "Ivan", "Ivanov")), byNumber);
    }

    @Test
    void update() throws SQLException {
        Account account = new Account(1, "55555444445555522222", 2000.1, new Client(1, "Ivan", "Ivanov"));
        Account byId = accountDao.getById(1);
        assertEquals(account, byId);

        account.setBalance(5555.5);
        accountDao.update(account);

        byId = accountDao.getById(1);
        assertEquals(account, byId);
    }
}