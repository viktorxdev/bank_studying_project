package ru.sberbank.viktormamontov.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;


public class Account {

    private long id;
    private String number;
    private double balance;
    @JsonIgnore
    private Client client;

    public Account(long id, String number, double balance, Client client) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.client = client;
    }

    public Account() {
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Double.compare(account.balance, balance) == 0
                && Objects.equals(number, account.number) && Objects.equals(client, account.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, balance, client);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                ", clientId=" + client.getId() +
                '}';
    }
}
