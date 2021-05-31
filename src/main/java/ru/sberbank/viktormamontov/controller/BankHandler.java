package ru.sberbank.viktormamontov.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;


public class BankHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        System.out.println(path);

        if (path.matches("^\\/accounts\\/\\d+\\/cards(\\/\\d+)?$")) {
            new CardHandler().handle(exchange);
        } else if (path.matches("^\\/accounts((\\/\\d+\\/balance)|(\\/\\d+))?$")) {
            new AccountHandler().handle(exchange);
        } else {

            //to do: 404 response
        }

    }
}
