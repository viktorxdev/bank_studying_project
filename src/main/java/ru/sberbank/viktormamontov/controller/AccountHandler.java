package ru.sberbank.viktormamontov.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sberbank.viktormamontov.service.BankService;
import ru.sberbank.viktormamontov.service.BankServiceImpl;

import java.io.IOException;

public class AccountHandler implements HttpHandler {

    private BankService bankService = BankServiceImpl.getInstance();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        String[] split = path.split("/");
        long id = Long.parseLong(split[2]);

        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equals("GET") && path.matches("\\/accounts\\/\\d+\\/balance")) {


        } else if (requestMethod.equals("PATCH") && path.matches("\\/accounts\\/\\d+")) {


        } else {

            // 404?
        }
    }
}
