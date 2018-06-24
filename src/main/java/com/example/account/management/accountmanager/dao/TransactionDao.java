package com.example.account.management.accountmanager.dao;

import com.example.account.management.accountmanager.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TransactionDao {
    private final List<Transaction> transactions = new ArrayList<>();

    public UUID save(final Transaction transaction) {
        transactions.add(transaction);
        return transaction.getId();
    }

    public List<Transaction> get(final String accountNumber) {
        return transactions.stream()
                           .filter(transaction -> transaction.getAccountId().equals(accountNumber))
                           .collect(Collectors.toList());
    }

    public void deleteAll() {
        transactions.clear();
    }
}
