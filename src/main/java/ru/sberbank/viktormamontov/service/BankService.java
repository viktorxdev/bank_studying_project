package ru.sberbank.viktormamontov.service;

import ru.sberbank.viktormamontov.entity.Card;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BankService {

    //if account with accountId has number equals to accountNumber, then generate and add new card
    void issueNewCard(long accountId, String accountNumber) throws Exception;

    //return list of all account's cards
    List<Card> getCardsByAccountId(long accountId) throws SQLException;

    // get account from db, update balance, put into db
    void topUpBalance(long accountId, double amount) throws SQLException;

    // return map with one entry, where key is string "balance" and value is balance
    Map<String, Double> checkBalance(long accountId) throws SQLException;
}
