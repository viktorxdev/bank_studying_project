package ru.sberbank.viktormamontov.service;

import ru.sberbank.viktormamontov.dao.AccountDao;
import ru.sberbank.viktormamontov.dao.AccountDaoImpl;
import ru.sberbank.viktormamontov.dao.CardDao;
import ru.sberbank.viktormamontov.dao.CardDaoImpl;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.entity.Card;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BankServiceImpl implements BankService {

    private AccountDao accountDao = AccountDaoImpl.getInstance();
    private CardDao cardDao = CardDaoImpl.getInstance();

    private static BankServiceImpl instance;
    private BankServiceImpl() {}

    public static BankServiceImpl getInstance() {
        if (instance == null) {
            instance = new BankServiceImpl();
        }
        return instance;
    }

    @Override
    public void issueNewCard(long accountId, String accountNumber) {
        Account account = accountDao.getById(accountId);
        if (account.getNumber().equals(accountNumber)) {
            Card newCard = generateCard(account);
            cardDao.add(newCard);
        }
    }

    @Override
    public List<Card> getCardsByAccountId(long accountId) {
        return cardDao.getCardsByAccountId(accountId);
    }

    @Override
    public void topUpBalance(long accountId, double amount) {
        Account account = accountDao.getById(accountId);
        account.setBalance(account.getBalance() + amount);
        accountDao.update(account);
    }

    @Override
    public Map<String, Double> checkBalance(long accountId) {
        Account account = accountDao.getById(accountId);
        String number = account.getNumber();
        double balance = account.getBalance();

        return Collections.singletonMap(number, balance);
    }

    private Card generateCard(Account account) {
        Random random = new Random();

        StringBuilder number = new StringBuilder();
        for (int i = 0; i < 19; i++) {
            if ((i + 1) % 5 == 0) {
                number.append(" ");
            } else {
                number.append(random.nextInt(10));
            }
        }

        LocalDate expiration = LocalDate.now().plusYears(4);

        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append(random.nextInt(10));
        }

        Card.Status status = Card.Status.ACTIVE;

        Card card = new Card();
        card.setNumber(number.toString());
        card.setExpiration(expiration);
        card.setCvv(cvv.toString());
        card.setStatus(status);
        card.setAccount(account);

        return card;
    }

}
