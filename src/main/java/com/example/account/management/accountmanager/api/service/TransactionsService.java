package com.example.account.management.accountmanager.api.service;

import com.example.account.management.accountmanager.model.Transaction;
import com.example.account.management.accountmanager.model.Type;

import java.util.List;
import java.util.UUID;

public interface TransactionsService {
    UUID execute(final String accountId,
                 final Double amount,
                 final Type type);

    List<Transaction> get(String number);

    void deleteAll();
}
