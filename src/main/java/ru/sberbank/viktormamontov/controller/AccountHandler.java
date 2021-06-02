package ru.sberbank.viktormamontov.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sberbank.viktormamontov.service.BankService;
import ru.sberbank.viktormamontov.service.BankServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class AccountHandler implements HttpHandler {

    private BankService bankService = BankServiceImpl.getInstance();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        String[] split = path.split("/");
        long id = Long.parseLong(split[2]);

        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equals("GET") && path.matches("\\/accounts\\/\\d+\\/balance")) {

            try {
                Map<String, Double> map = bankService.getBalance(id);
                String response = new ObjectMapper().writeValueAsString(map);
                BankHandler.sendResponse(200, response.getBytes(), exchange);

            } catch (SQLException throwable) {
//                throwable.printStackTrace();
                BankHandler.sendResponse(404, throwable.getMessage().getBytes(), exchange);
            }

        } else if (requestMethod.equals("PATCH") && path.matches("\\/accounts\\/\\d+")) {

            try {
                Scanner scanner = new Scanner(exchange.getRequestBody());
                String requestBody = scanner.nextLine();

                Map<String, Double> map = new ObjectMapper().readValue(requestBody, new TypeReference<Map<String, Double>>() {});
                if (!map.containsKey("amount")) {
                    BankHandler.sendResponse(404, "Wrong input data".getBytes(), exchange);
                    return;
                }
                bankService.topUpBalance(id, map.get("amount"));
                BankHandler.sendResponse(200, "".getBytes(), exchange);

            } catch (SQLException throwable) {
//                throwable.printStackTrace();
                BankHandler.sendResponse(404, throwable.getMessage().getBytes(), exchange);
            }

        } else {
            BankHandler.sendResponse(404, "invalid method or URL".getBytes(), exchange);
        }
    }

}
