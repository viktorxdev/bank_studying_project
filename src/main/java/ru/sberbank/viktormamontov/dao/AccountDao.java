package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.entity.Account;

import java.sql.SQLException;

public interface AccountDao {

    Account getById(long id) throws SQLException;

    void update(Account account) throws SQLException;

}
