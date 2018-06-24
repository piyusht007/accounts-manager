package com.example.account.management.accountmanager.model;

import com.example.account.management.accountmanager.service.InsufficientBalanceException;

public enum Type {
    CREDIT {
        @Override
        public Double getNewBalance(final Double currentBalance,
                                    final Double transactionAmount) {
            return currentBalance + transactionAmount;
        }
    },
    DEBIT {
        @Override
        public Double getNewBalance(final Double currentBalance,
                                    final Double transactionAmount) {
            if( currentBalance < transactionAmount ) {
                throw new InsufficientBalanceException("Insufficient funds in the account. Available Funds: "
                                                       + currentBalance
                                                       + ". Debit Amount: "
                                                       + transactionAmount);
            } else {
                return currentBalance - transactionAmount;
            }
        }
    };

    public abstract Double getNewBalance(final Double currentBalance,
                                         final Double transactionAmount);
}
