package com.example.account.management.accountmanager.api;

import java.util.Optional;

public class SecondaryAccountCreateRequest {

    private String customerId;
    private String initialCredit;

    public SecondaryAccountCreateRequest(final String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Optional<String> getInitialCredit() {
        return Optional.ofNullable(initialCredit);
    }

    public void setInitialCredit(final String initialCredit) {
        this.initialCredit = initialCredit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecondaryAccountCreateRequest{");
        sb.append("customerId='").append(customerId).append('\'');
        sb.append(", initialCredit='").append(initialCredit).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
