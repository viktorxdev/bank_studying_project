package ru.sberbank.viktormamontov.controller;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class BankServer {
    private HttpServer server;

    public BankServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", new BankHandler());
            server.setExecutor(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        server.start();
    }
    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    public HttpServer getServer() {
        return server;
    }

    public void setServer(HttpServer server) {
        this.server = server;
    }
}
