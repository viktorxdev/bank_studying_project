package ru.sberbank.viktormamontov.service;

import ru.sberbank.viktormamontov.entity.Card;

import java.util.List;
import java.util.Map;

public interface BankService {

    //if account with accountId has number equals to accountNumber, then generate and add new card
    void issueNewCard(long accountId, String accountNumber);

    //return list of all account's cards
    List<Card> getCardsByAccountId(long accountId);

    // get account from db, update balance, put into db
    void topUpBalance(long accountId, double amount);

    // return map with one entry, where key is string "balance" and value is balance
    Map<String, Double> checkBalance(long accountId);
}
