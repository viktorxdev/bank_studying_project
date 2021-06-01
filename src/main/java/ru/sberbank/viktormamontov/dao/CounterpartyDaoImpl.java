package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.DbUtil;
import ru.sberbank.viktormamontov.dao.mapper.AccountMapper;
import ru.sberbank.viktormamontov.dao.mapper.CounterpartyMapper;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.entity.Counterparty;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CounterpartyDaoImpl implements CounterpartyDao{

    private static CounterpartyDaoImpl instance;
    private CounterpartyDaoImpl() {}

    public static CounterpartyDaoImpl getInstance() {
        if (instance == null) {
            instance = new CounterpartyDaoImpl();
        }
        return instance;
    }

    @Override
    public Counterparty getById(long id) throws SQLException {
        Counterparty counterparty = null;
        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM counterparties WHERE id=?")){

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            counterparty = CounterpartyMapper.getCounterpartyFromResultSet(resultSet);
        }
        return counterparty;
    }

    @Override
    public Counterparty getByName(String name) throws SQLException {
        Counterparty counterparty = null;
        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM counterparties WHERE name=?")){

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            counterparty = CounterpartyMapper.getCounterpartyFromResultSet(resultSet);
        }
        return counterparty;
    }


    @Override
    public void add(Counterparty counterparty) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement =
                     conn.prepareStatement("INSERT INTO counterparties(name, information) VALUES (?,?)")){

            statement.setString(1, counterparty.getName());
            statement.setString(2, counterparty.getInformation());

            statement.executeUpdate();
        }
        Counterparty withId = getByName(counterparty.getName());
        counterparty.setId(withId.getId());

        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement = conn.prepareStatement("INSERT INTO clients_counterparties VALUES (?, ?)")) {

            // ATTENTION! client id is hardcoded!
            statement.setLong(1, 1);
            statement.setLong(2, counterparty.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public List<Counterparty> getAllByClientId(long clientId) throws SQLException {
        List<Counterparty> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
        PreparedStatement statement = conn.prepareStatement("SELECT cp.ID, NAME, INFORMATION FROM COUNTERPARTIES cp\n" +
                "JOIN CLIENTS_COUNTERPARTIES cc ON cp.ID = cc.COUNTERPARTY_ID\n" +
                "JOIN CLIENTS C on C.ID = cc.CLIENT_ID\n" +
                "WHERE C.ID = ?")) {

            statement.setLong(1, clientId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Counterparty counterparty = CounterpartyMapper.getCounterpartyFromResultSet(resultSet);
                list.add(counterparty);
            }
        }
        return list;
    }

    @Override
    public void update(Counterparty counterparty) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DbUtil.URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement =
                     conn.prepareStatement("UPDATE counterparties SET name =?, information =? WHERE id =?")){

            statement.setString(1, counterparty.getName());
            statement.setString(2, counterparty.getInformation());
            statement.setLong(3, counterparty.getId());

            statement.executeUpdate();
        }
    }
}
