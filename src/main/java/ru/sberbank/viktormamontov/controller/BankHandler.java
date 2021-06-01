package ru.sberbank.viktormamontov.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;


public class BankHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.matches("^\\/accounts\\/\\d+\\/cards(\\/\\d+)?$")) {
            new CardHandler().handle(exchange);
        } else if (path.matches("^\\/accounts((\\/\\d+\\/balance)|(\\/\\d+))?$")) {
            new AccountHandler().handle(exchange);
        } else if (path.matches("^\\/counterparties(\\/\\d+)?$")) {
            new CounterpartyHandler().handle(exchange);
        }else {
            sendResponse(404, "invalid URL".getBytes(), exchange);
        }

    }

    public static void sendResponse(int code, byte[] response, HttpExchange exchange) {
        try {
            exchange.sendResponseHeaders(code, response.length);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(response);
            exchange.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
