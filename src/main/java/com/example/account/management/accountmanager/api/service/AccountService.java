package com.example.account.management.accountmanager.api.service;

import com.example.account.management.accountmanager.api.AccountCreateRequest;
import com.example.account.management.accountmanager.api.AccountCreateResponse;
import com.example.account.management.accountmanager.api.AccountsInfo;
import com.example.account.management.accountmanager.api.SecondaryAccountCreateRequest;

public interface AccountService {

    AccountCreateResponse create(final AccountCreateRequest aAccountCreateRequest);

    AccountCreateResponse create(final SecondaryAccountCreateRequest secondaryAccountCreateRequest);

    AccountsInfo get(final String customerId);

    void deleteAll();
}
