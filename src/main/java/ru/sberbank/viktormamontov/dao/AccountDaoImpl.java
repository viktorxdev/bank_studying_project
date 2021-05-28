package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.DbUtil;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.entity.mapper.AccountMapper;

import java.sql.*;

public class AccountDaoImpl implements AccountDao {

    private static AccountDaoImpl instance;
    private AccountDaoImpl() {}

    public static AccountDaoImpl getInstance() {
        if (instance == null) {
            instance = new AccountDaoImpl();
        }
        return instance;
    }

    @Override
    public Account getById(long id) {
        Account account = null;
        try (Connection conn = DriverManager.getConnection(DbUtil.DB_URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE id=?")){

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            account = AccountMapper.getAccountFromResultSet(resultSet);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return account;
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public double getBalance(long id) {
        return 0;
    }
}
