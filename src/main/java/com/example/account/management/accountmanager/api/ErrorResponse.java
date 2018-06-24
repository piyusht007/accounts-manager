package com.example.account.management.accountmanager.api;

public class ErrorResponse {

    private String details;

    public ErrorResponse(final String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
