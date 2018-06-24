package com.example.account.management.accountmanager.api;

import java.util.List;

public class AccountsInfo {

    private final List<AccountSummary> accountSummaries;

    public AccountsInfo(final List<AccountSummary> accountSummaries) {
        this.accountSummaries = accountSummaries;
    }

    public List<AccountSummary> getAccountSummaries() {
        return accountSummaries;
    }
}
