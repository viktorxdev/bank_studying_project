package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.entity.Client;

public interface ClientDao {

    Client getById(long id);
}
