package ru.sberbank.viktormamontov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.viktormamontov.controller.BankServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CounterpartyApiTest {

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
    void getCounterparties() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/counterparties"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("\"id\":1,\"name\":\"some company\",\"information\":\"some information\""));
    }

    @Test
    void postCounterparty() throws IOException, InterruptedException {

        String json = "{\"name\":\"company from postman\",\"information\":\"some information\",\"balance\":11.11}";
        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(json.toString());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/counterparties"))
                .POST(jsonPayload)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/counterparties"))
                .GET().build();
        HttpResponse<String> response1 = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response1.body().contains("\"name\":\"company from postman\",\"information\":\"some information\",\"balance\":11.11"));
    }
}
