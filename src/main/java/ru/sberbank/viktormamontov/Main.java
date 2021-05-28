package ru.sberbank.viktormamontov;


import ru.sberbank.viktormamontov.dao.*;
import ru.sberbank.viktormamontov.entity.Account;
import ru.sberbank.viktormamontov.entity.Card;
import ru.sberbank.viktormamontov.entity.Client;
import ru.sberbank.viktormamontov.service.BankService;
import ru.sberbank.viktormamontov.service.BankServiceImpl;

import java.time.LocalDate;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        BankService bankService = BankServiceImpl.getInstance();
        bankService.issueNewCard(1, "55555444445555522222");

//        ClientDao clientDao = ClientDaoImpl.getInstance();
//        Client byId = clientDao.getById(1l);
//        System.out.println(byId);

//        AccountDao accountDao = AccountDaoImpl.getInstance();
//        Account byId = accountDao.getById(1);
//        byId.setBalance(822.44);
//        accountDao.update(byId);

//        System.out.println(byId);

//        CardDao cardDao = CardDaoImpl.getInstance();
//
//        List<Card> cards = cardDao.getCardsByAccountId(2);
//        cards.forEach(System.out::println);

//        Card newCard = new Card();
//        newCard.setNumber("0001 0020 0300 4000");
//        newCard.setExpiration(LocalDate.now().plusYears(3));
//        newCard.setCvv("010");
//        newCard.setStatus(Card.Status.EXPIRED);
//        newCard.setAccount(byId);
//
//        cardDao.add(newCard);
//
//        List<Card> cards = cardDao.getCardsByAccountId(1);
//        cards.forEach(System.out::println);
//        System.out.println();
//        System.out.println(newCard);



    }
}
