package ru.sberbank.viktormamontov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.viktormamontov.controller.BankServer;
import ru.sberbank.viktormamontov.dao.CounterpartyDao;
import ru.sberbank.viktormamontov.dao.CounterpartyDaoImpl;
import ru.sberbank.viktormamontov.entity.Counterparty;
import ru.sberbank.viktormamontov.service.BankService;
import ru.sberbank.viktormamontov.service.BankServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.Map;

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
    void postCounterparties() throws IOException, InterruptedException {

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
        HttpResponse<String> responseWithNewObject = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(responseWithNewObject.body()
                .contains("\"name\":\"company from postman\",\"information\":\"some information\",\"balance\":11.11"));
    }

    @Test
    void patchCounterparty() throws IOException, InterruptedException, SQLException {

        BankService service = BankServiceImpl.getInstance();
        double balanceBefore = service.getBalance(1).get("balance");

        CounterpartyDao counterpartyDao = CounterpartyDaoImpl.getInstance();
        Counterparty counterparty = counterpartyDao.getById(1);
        double counterpartyBalanceBefore = counterparty.getBalance();

        String json = "{\"amount\":5000.00,\"number\":\"55555444445555522222\"}";
        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(json.toString());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/counterparties/1"))
                .method("PATCH", jsonPayload)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Double balanceAfter = service.getBalance(1).get("balance");
        assertTrue(balanceBefore - balanceAfter == 5000.0);

        counterparty = counterpartyDao.getById(1);
        double counterpartyBalanceAfter = counterparty.getBalance();
        assertTrue(counterpartyBalanceAfter - counterpartyBalanceBefore == 5000.0);
    }
}
