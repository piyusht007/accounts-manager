package com.example.account.management.accountmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private LocalDateTime date;
    private Type type;
    private Double amount;
    private String accountId;

    public Transaction(final UUID id,
                       final LocalDateTime date,
                       final Type type,
                       final Double amount,
                       final String accountId) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    @JsonIgnore
    public String getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("date=").append(date);
        sb.append(", type=").append(type);
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
