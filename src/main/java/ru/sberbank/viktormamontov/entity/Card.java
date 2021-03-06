package ru.sberbank.viktormamontov.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Objects;


public class Card {

    private long id;
    private String number;
    private LocalDate expiration;
    private String cvv;
    private Status status;
    @JsonIgnore
    private Account account;

    public Card(long id, String number, LocalDate expiration, String cvv, Status status, Account account) {
        this.id = id;
        this.number = number;
        this.expiration = expiration;
        this.cvv = cvv;
        this.status = status;
        this.account = account;
    }

    public Card() {
    }

    public enum Status {
        ACTIVE, EXPIRED
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

    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id && Objects.equals(number, card.number) && Objects.equals(expiration, card.expiration) && Objects.equals(cvv, card.cvv) && status == card.status && Objects.equals(account, card.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, expiration, cvv, status, account);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", expiration=" + expiration +
                ", cvv=" + cvv +
                ", status=" + status +
                ", accountId=" + account.getId() +
                '}';
    }
}
