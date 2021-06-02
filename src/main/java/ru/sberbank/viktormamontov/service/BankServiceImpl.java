package ru.sberbank.viktormamontov.service;

import ru.sberbank.viktormamontov.dao.*;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.entity.Card;
import ru.sberbank.viktormamontov.entity.Counterparty;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BankServiceImpl implements BankService {

    private AccountDao accountDao = AccountDaoImpl.getInstance();
    private CardDao cardDao = CardDaoImpl.getInstance();
    private CounterpartyDao counterpartyDao = CounterpartyDaoImpl.getInstance();

    private static BankServiceImpl instance;
    private BankServiceImpl() {}

    public static BankServiceImpl getInstance() {
        if (instance == null) {
            instance = new BankServiceImpl();
        }
        return instance;
    }

    @Override
    public void issueNewCard(long accountId, String accountNumber) throws Exception {
        Account account = accountDao.getById(accountId);
        if (account.getNumber().equals(accountNumber)) {
            Card newCard = generateCard(account);
            cardDao.add(newCard);
        } else {
            throw new Exception("invalid number");
        }
    }

    @Override
    public List<Card> getCardsByAccountId(long accountId) throws SQLException {
        return cardDao.getCardsByAccountId(accountId);
    }

    @Override
    public void topUpBalance(long accountId, double amount) throws SQLException {
        Account account = accountDao.getById(accountId);
        account.setBalance(account.getBalance() + amount);
        accountDao.update(account);
    }

    @Override
    public Map<String, Double> getBalance(long accountId) throws SQLException {
        Account account = accountDao.getById(accountId);
        double balance = account.getBalance();

        return Collections.singletonMap("balance", balance);
    }

    @Override
    public void addNewCounterparty(Counterparty counterparty, long clientId) throws SQLException {
        counterpartyDao.add(counterparty, clientId);
    }

    @Override
    public List<Counterparty> getCounterpartiesByClientId(long clientId) throws SQLException {
        return counterpartyDao.getAllByClientId(clientId);
    }

    @Override
    public void transferMoney(double amount, long counterpartyId, String accountNumber) throws SQLException {
        Account account = accountDao.getByNumber(accountNumber);
        account.setBalance(account.getBalance() - amount);
        accountDao.update(account);

        Counterparty byId = counterpartyDao.getById(counterpartyId);
        byId.setBalance(byId.getBalance() + amount);
        counterpartyDao.update(byId);
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
