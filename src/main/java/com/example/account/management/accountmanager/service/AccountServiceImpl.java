package com.example.account.management.accountmanager.service;

import com.example.account.management.accountmanager.api.*;
import com.example.account.management.accountmanager.api.service.CustomerService;
import com.example.account.management.accountmanager.api.service.TransactionsService;
import com.example.account.management.accountmanager.dao.AccountDao;
import com.example.account.management.accountmanager.dao.CustomerDao;
import com.example.account.management.accountmanager.model.Account;
import com.example.account.management.accountmanager.model.Customer;
import com.example.account.management.accountmanager.model.Transaction;
import com.example.account.management.accountmanager.model.Type;
import com.example.account.management.accountmanager.api.service.AccountService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionsService transactionsService;

    @Override
    public AccountCreateResponse create(final AccountCreateRequest accountCreateRequest) {
        validateAccountCreateRequest(accountCreateRequest);

        final CustomerCreateRequest customerCreateRequest = prepareCustomerCreateRequest(accountCreateRequest.getFirstName(),
                                                                                         accountCreateRequest.getLastName());

        final String customerId = customerService.create(customerCreateRequest);
        final Account account = createAccount(String.valueOf(customerId), accountCreateRequest.getInitialCredit());
        return new AccountCreateResponse(account.getNumber(), account.getCustomerId());
    }

    @Override
    public AccountCreateResponse create(final SecondaryAccountCreateRequest secondaryAccountCreateRequest) {
        validateSecondaryAccountCreateRequest(secondaryAccountCreateRequest);

        final String customerId = secondaryAccountCreateRequest.getCustomerId();
        final Customer customer = customerService.get(customerId);
        final Account account = createAccount(customerId, secondaryAccountCreateRequest.getInitialCredit());
        return new AccountCreateResponse(account.getNumber(), customer.getId());
    }

    @Override
    public AccountsInfo get(final String accountId,
                            final String customerId) {
        final Customer customer = customerService.get(customerId);
        final List<Account> accounts = accountDao.getByCustomerId(accountId, customerId);

        if( accounts == null || accounts.isEmpty() ) {
            throw new AccountsNotFoundException(Errors.ACCOUNTS_NOT_FOUND.getMessage() + accountId + ", " + customerId);
        } else {
            final List<AccountSummary> accountSummaries = new ArrayList<>();

            for( final Account account : accounts ) {
                final List<Transaction> transactions = transactionsService.get(account.getNumber());
                final AccountSummary accountSummary = new AccountSummary(account.getNumber(),
                                                                         customer.getId(),
                                                                         customer.getFirstName(),
                                                                         customer.getLastName(),
                                                                         account.getBalance(),
                                                                         transactions);

                accountSummaries.add(accountSummary);
            }

            return new AccountsInfo(accountSummaries);
        }
    }

    @Override
    public void deleteAll() {
        accountDao.deleteAll();
    }

    private Account createAccount(final String customerId,
                                  final Optional<String> initialCredit) {
        final Account account = mapToAccount(customerId);

        accountDao.save(account);

        if( shouldExecuteTransaction(initialCredit) ) {
            transactionsService.execute(account.getNumber(), Double.valueOf(initialCredit.get()), Type.CREDIT);
        }

        return account;
    }

    private CustomerCreateRequest prepareCustomerCreateRequest(final String firstName,
                                                               final String lastName) {
        return new CustomerCreateRequest(firstName, lastName);
    }

    private boolean shouldExecuteTransaction(final Optional<String> initialCreditOptional) {
        return initialCreditOptional.isPresent();
    }

    private Account mapToAccount(final String customerId) {
        final String accountNumber = String.valueOf(Math.abs(new Random().nextLong()));
        final Account account = new Account(accountNumber, customerId, Double.valueOf(0));
        return account;
    }

    private void validateAccountCreateRequest(final AccountCreateRequest accountCreateRequest) {
        Preconditions.checkArgument(accountCreateRequest != null, "Account create request cannot be null");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(accountCreateRequest.getFirstName()),
                                    "Customer first name cannot be empty/null.");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(accountCreateRequest.getLastName()),
                                    "Customer last name cannot be empty/null.");
    }

    private void validateSecondaryAccountCreateRequest(final SecondaryAccountCreateRequest secondaryAccountCreateRequest) {
        Preconditions.checkArgument(secondaryAccountCreateRequest != null, "Secondary account create request cannot be null");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(secondaryAccountCreateRequest.getCustomerId()),
                                    "Customer id cannot be null/empty");
    }
}
