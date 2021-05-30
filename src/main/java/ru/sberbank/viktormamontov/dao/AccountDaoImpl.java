package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.DbUtil;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.dao.mapper.AccountMapper;

import java.math.BigDecimal;
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
        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE id=?")){

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            account = AccountMapper.getAccountFromResultSet(resultSet);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return account;
    }

    @Override
    public void update(Account account) {
        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement =
                conn.prepareStatement("UPDATE accounts SET number =?, balance =?, client_id =? WHERE id =?")){

            statement.setString(1, account.getNumber());
            statement.setBigDecimal(2, BigDecimal.valueOf(account.getBalance()));
            statement.setLong(3, account.getClient().getId());
            statement.setLong(4, account.getId());

            statement.executeUpdate();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
