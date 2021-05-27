package ru.sberbank.viktormamontov.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Card {

    private long id;
    private String number;
    private LocalDate expiration;
    private int cvv;
    private Account account;

    public Card(String number, LocalDate expiration, int cvv, Account account) {
        this.number = number;
        this.expiration = expiration;
        this.cvv = cvv;
        this.account = account;
    }

    public Card() {
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

    public LocalDate getExpiration() {
        return expiration;
    }
    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id && cvv == card.cvv && Objects.equals(number, card.number) && Objects.equals(expiration, card.expiration) && Objects.equals(account, card.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, expiration, cvv, account);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", expiration=" + expiration +
                ", cvv=" + cvv +
                ", account=" + account +
                '}';
    }
}
