package com.example.account.management.accountmanager.api;

public enum Errors {
    CUSTOMER_NOT_FOUND("Customer not found with id: "),
    ACCOUNTS_NOT_FOUND("No accounts found for customer id: ");

    private String message;

    Errors(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
