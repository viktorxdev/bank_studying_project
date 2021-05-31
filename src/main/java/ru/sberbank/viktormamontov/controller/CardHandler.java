package ru.sberbank.viktormamontov.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sberbank.viktormamontov.entity.Card;
import ru.sberbank.viktormamontov.service.BankService;
import ru.sberbank.viktormamontov.service.BankServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CardHandler implements HttpHandler {

    private BankService bankService = BankServiceImpl.getInstance();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        String[] split = path.split("/");
        long accountId = Long.parseLong(split[2]);

        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equals("POST") && path.matches("^\\/accounts\\/\\d+\\/cards$")) {

            try {
                Scanner scanner = new Scanner(exchange.getRequestBody());
                String requestBody = scanner.nextLine();

                Map<String, String> map = new ObjectMapper().readValue(requestBody, new TypeReference<Map<String, String>>() {
                });
                bankService.issueNewCard(accountId, map.get("number"));
                BankHandler.sendResponse(200, "".getBytes(), exchange);

            } catch (Exception e) {
                e.printStackTrace();
                BankHandler.sendResponse(404, e.getMessage().getBytes(), exchange);
            }

        } else if (requestMethod.equals("GET") && path.matches("^\\/accounts\\/\\d+\\/cards$")) {

            try {
                List<Card> cards = bankService.getCardsByAccountId(accountId);

                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                String response = mapper.writeValueAsString(cards);
                BankHandler.sendResponse(200, response.getBytes(), exchange);

            } catch (SQLException throwable) {
                throwable.printStackTrace();
                BankHandler.sendResponse(404, throwable.getMessage().getBytes(), exchange);
            }

        } else {
            BankHandler.sendResponse(404, "invalid method or URL".getBytes(), exchange);
        }
    }
}
