package com.example.account.management.accountmanager.api;

public enum Errors {
    CUSTOMER_NOT_FOUND("Customer not found with id: "),
    ACCOUNTS_NOT_FOUND("No records found for account id, customer id: ");

    private String message;

    Errors(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
