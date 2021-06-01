package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.entity.Counterparty;

import java.util.List;

public interface CounterpartyDao {

    Counterparty getById(long id);

    void add(Counterparty counterparty);

    List<Counterparty> getAll();

    void update(Counterparty counterparty);
}
