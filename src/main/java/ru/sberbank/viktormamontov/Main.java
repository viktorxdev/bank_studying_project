package ru.sberbank.viktormamontov;

import ru.sberbank.viktormamontov.controller.BankServer;



public class Main {

    public static void main(String[] args) {

        DbUtil.createAndFillTables();

        new BankServer().start();
    }
}
