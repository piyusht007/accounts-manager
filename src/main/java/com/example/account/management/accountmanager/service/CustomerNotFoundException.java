package com.example.account.management.accountmanager.service;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(final String message) {
        super(message);
    }
}
