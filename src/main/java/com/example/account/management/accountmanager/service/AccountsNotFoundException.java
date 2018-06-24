package com.example.account.management.accountmanager.service;

public class AccountsNotFoundException extends RuntimeException {

    public AccountsNotFoundException(final String message) {
        super(message);
    }
}
