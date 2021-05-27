package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.entity.Account;

public interface AccountDAO {

    Account getById(long id);

    void update(Account account);

    double getBalance(long id);
}
