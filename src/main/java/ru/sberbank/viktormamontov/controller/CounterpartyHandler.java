package ru.sberbank.viktormamontov.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sberbank.viktormamontov.entity.Counterparty;
import ru.sberbank.viktormamontov.service.BankService;
import ru.sberbank.viktormamontov.service.BankServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CounterpartyHandler implements HttpHandler {

    private BankService bankService = BankServiceImpl.getInstance();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equals("GET") && path.matches("^\\/counterparties$")) {

            try {
                // ATTENTION! hardcoded client id
                List<Counterparty> counterparties = bankService.getCounterpartiesByClientId(1);
                String response = new ObjectMapper().writeValueAsString(counterparties);
                BankHandler.sendResponse(200, response.getBytes(), exchange);

            } catch (SQLException throwable) {
//                throwable.printStackTrace();
                BankHandler.sendResponse(404, throwable.getMessage().getBytes(), exchange);
            }

        } else if (requestMethod.equals("POST") && path.matches("^\\/counterparties$")) {

            try {
                Scanner scanner = new Scanner(exchange.getRequestBody());
                String requestBody = scanner.nextLine();

                Counterparty counterparty = new ObjectMapper().readValue(requestBody, Counterparty.class);

                // ATTENTION! hardcoded client id
                bankService.addNewCounterparty(counterparty, 1);
                BankHandler.sendResponse(200, "".getBytes(), exchange);

            } catch (SQLException throwable) {
//                throwable.printStackTrace();
                BankHandler.sendResponse(404, throwable.getMessage().getBytes(), exchange);
            }

        } else if (requestMethod.equals("PATCH") && path.matches("^\\/counterparties\\/\\d+$")) {

            try {
                String[] split = path.split("/");
                long id = Long.parseLong(split[2]);

                Scanner scanner = new Scanner(exchange.getRequestBody());
                String requestBody = scanner.nextLine();

                Map<String, Double> map = new ObjectMapper().readValue(requestBody, new TypeReference<Map<String, Double>>() {});
                if (!map.containsKey("amount")) {
                    BankHandler.sendResponse(404, "Wrong input data".getBytes(), exchange);
                    return;
                }
                bankService.transferMoney(map.get("amount"), id);
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
