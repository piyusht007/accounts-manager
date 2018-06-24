package com.example.account.management.accountmanager.api;

public class AccountCreateResponse {

    private String accountId;
    private String customerId;

    public AccountCreateResponse(final String accountId,
                                 final String customerId) {
        this.accountId = accountId;
        this.customerId = customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountCreateResponse{");
        sb.append("accountId='").append(accountId).append('\'');
        sb.append(", customerId='").append(customerId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
