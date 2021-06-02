package ru.sberbank.viktormamontov;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.viktormamontov.controller.BankServer;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AccountAndCardApiTest {

    BankServer server;
    HttpClient client;
    HttpRequest request;

    @BeforeEach
    void setUp() {
        DbUtil.createAndFillTables();
        server = new BankServer();
        client = HttpClient.newHttpClient();
        server.start();
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    void getBalance() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1/balance"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("2000.1"));
    }

    @Test
    void getBalanceWrongId() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/122/balance"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response.body().contains("No data is available"));
        assertEquals(404, response.statusCode());
    }

    @Test
    void getCards() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1/cards"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("1111 3333 2222 8888"));
    }

    @Test
    void getCardsWrongId() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/122/cards"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response.body().contains("No data is available"));
        assertEquals(404, response.statusCode());
    }

    @Test
    void patchBalance() throws IOException, InterruptedException {

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1/balance"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, Double> map = new ObjectMapper().readValue(response.body(), new TypeReference<Map<String, Double>>() {
        });
        double balanceBefore = map.get("balance");

        String json = "{\"amount\":55555.0}";
        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(json.toString());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1"))
                .method("PATCH", jsonPayload)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1/balance"))
                .GET().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        map = new ObjectMapper().readValue(response.body(), new TypeReference<Map<String, Double>>() {
        });
        double balanceAfter = map.get("balance");

        assertEquals(55555.0, balanceAfter-balanceBefore);
    }

    @Test
    void patchBalanceWrongInputJSON() throws IOException, InterruptedException {

        String json = "{\"fail\":55555.0}";
        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(json.toString());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1"))
                .method("PATCH", jsonPayload)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response.body().contains("Wrong input data"));
        assertEquals(404, response.statusCode());
    }

    @Test
    void postNewCard() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1/cards"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int listCardsLengthBefore = response.body().length();

        String json = "{\"number\":\"55555444445555522222\"}";
        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(json.toString());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1/cards"))
                .POST(jsonPayload)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1/cards"))
                .GET().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int listCardsLengthAfter = response.body().length();

        assertTrue(listCardsLengthAfter > listCardsLengthBefore);
    }

    @Test
    void postNewCardWrongInputJSON() throws IOException, InterruptedException {
        String json = "{\"wrong\":\"55555444445555522222\"}";
        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(json.toString());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1/cards"))
                .POST(jsonPayload)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response.body().contains("Wrong input data"));
        assertEquals(404, response.statusCode());
    }
}