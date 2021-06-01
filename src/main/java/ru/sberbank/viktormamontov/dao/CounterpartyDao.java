package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.entity.Counterparty;

import java.sql.SQLException;
import java.util.List;

public interface CounterpartyDao {

    Counterparty getById(long id) throws SQLException;

    Counterparty getByName(String name) throws SQLException;

    void add(Counterparty counterparty, long clientId) throws SQLException;

    List<Counterparty> getAllByClientId(long clientId) throws SQLException;

    void update(Counterparty counterparty) throws SQLException;
}
