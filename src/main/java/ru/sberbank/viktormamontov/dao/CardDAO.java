package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.entity.Card;

import java.util.List;

public interface CardDAO {

    Card getById(long id);

    void add(Card card);

    List<Card> getCardsByAccountId(long accountId);
}
