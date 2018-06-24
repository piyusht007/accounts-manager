package com.example.account.management.accountmanager.api;

import java.util.Optional;

public class AccountCreateRequest {
    private String firstName;
    private String lastName;
    private String initialCredit;

    public AccountCreateRequest() {
    }

    public AccountCreateRequest(final String firstName,
                                final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Optional<String> getInitialCredit() {
        return Optional.ofNullable(initialCredit);
    }

    public void setInitialCredit(final String initialCredit) {
        this.initialCredit = initialCredit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountCreateRequest{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", initialCredit='").append(initialCredit).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
