package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.entity.Client;

public interface ClientDAO {

    Client getById(long id);
}
