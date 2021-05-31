package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.entity.Client;

import java.sql.SQLException;

public interface ClientDao {

    Client getById(long id) throws SQLException;
}
