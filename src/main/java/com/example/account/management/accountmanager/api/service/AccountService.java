package com.example.account.management.accountmanager.api.service;

import com.example.account.management.accountmanager.api.AccountCreateRequest;
import com.example.account.management.accountmanager.api.AccountsInfo;
import com.example.account.management.accountmanager.service.AccountsNotFoundException;
import com.example.account.management.accountmanager.service.CustomerNotFoundException;
import com.example.account.management.accountmanager.api.SecondaryAccountCreateRequest;
import com.example.account.management.accountmanager.api.AccountCreateResponse;
import com.example.account.management.accountmanager.service.InsufficientBalanceException;

import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {

    AccountCreateResponse create(final AccountCreateRequest aAccountCreateRequest);

    AccountCreateResponse create(final SecondaryAccountCreateRequest secondaryAccountCreateRequest);

    AccountsInfo get(final String accountId, final String customerId);

    void deleteAll();
}
