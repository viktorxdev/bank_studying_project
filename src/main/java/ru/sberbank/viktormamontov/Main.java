package ru.sberbank.viktormamontov;

import ru.sberbank.viktormamontov.controller.BankServer;

import java.util.Locale;


public class Main {

    public static void main(String[] args) {

        DbUtil.createAndFillTables();

        new BankServer().start();
    }
}
