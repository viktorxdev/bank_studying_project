package ru.sberbank.viktormamontov.entity;

import java.util.Objects;

public class Counterparty {

    private long id;
    private String name;
    private String information;
    private double balance;

    public Counterparty(long id, String name, String information, double balance) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.balance = balance;
    }

    public Counterparty() {
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }
    public void setInformation(String information) {
        this.information = information;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Counterparty that = (Counterparty) o;
        return id == that.id && Double.compare(that.balance, balance) == 0 && Objects.equals(name, that.name) && Objects.equals(information, that.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, information, balance);
    }

    @Override
    public String toString() {
        return "Counterparty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", balance=" + balance +
                '}';
    }
}
