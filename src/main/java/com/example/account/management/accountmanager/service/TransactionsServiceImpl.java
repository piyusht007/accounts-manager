package com.example.account.management.accountmanager.service;

import com.example.account.management.accountmanager.api.service.TransactionsService;
import com.example.account.management.accountmanager.dao.AccountDao;
import com.example.account.management.accountmanager.dao.TransactionDao;
import com.example.account.management.accountmanager.model.Account;
import com.example.account.management.accountmanager.model.Transaction;
import com.example.account.management.accountmanager.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    @Async
    public UUID execute(final String accountId,
                        final Double transactionAmount,
                        final Type type) {
        final UUID transactionId = UUID.randomUUID();
        final LocalDateTime transactionDateTime = LocalDateTime.now();
        final Transaction transaction = new Transaction(transactionId, transactionDateTime, type, transactionAmount, accountId);

        updateAccountBalance(accountId, transactionAmount, type);
        transactionDao.save(transaction);
        return transactionId;
    }

    @Override
    public List<Transaction> get(final String accountNumber) {
        return transactionDao.get(accountNumber);
    }

    @Override
    public void deleteAll() {
        transactionDao.deleteAll();
    }

    private void updateAccountBalance(final String accountId,
                                      final Double transactionAmount,
                                      final Type type) {
        final Account account = accountDao.get(accountId).orElseThrow(() -> new RuntimeException(""));
        final Double newBalance = type.getNewBalance(account.getBalance(), transactionAmount);

        account.setBalance(newBalance);
        accountDao.save(account);
    }
}
