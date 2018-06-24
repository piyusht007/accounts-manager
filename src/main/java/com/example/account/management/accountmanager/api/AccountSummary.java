package com.example.account.management.accountmanager.api;

import com.example.account.management.accountmanager.model.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class AccountSummary {

    private String accountId;
    private String customerId;
    private String firstName;
    private String lastName;
    private Double balance;
    private List<Transaction> transactions;

    public AccountSummary(final String accountId,
                        final String customerId,
                        final String firstName,
                        final String lastName,
                        final Double balance,
                        final List<Transaction> transactions) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.transactions = transactions;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountsInfo{");
        sb.append("accountId=").append(accountId);
        sb.append(", customerId='").append(customerId).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", balance=").append(balance);
        sb.append(", transactions=").append(transactions);
        sb.append('}');
        return sb.toString();
    }
}
