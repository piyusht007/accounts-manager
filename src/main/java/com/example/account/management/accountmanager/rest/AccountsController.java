package com.example.account.management.accountmanager.rest;

import com.example.account.management.accountmanager.api.AccountCreateRequest;
import com.example.account.management.accountmanager.api.SecondaryAccountCreateRequest;
import com.example.account.management.accountmanager.api.AccountCreateResponse;
import com.example.account.management.accountmanager.api.AccountsInfo;
import com.example.account.management.accountmanager.api.service.AccountService;
import com.example.account.management.accountmanager.service.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/accounts-management/accounts")
public class AccountsController implements AccountService {

    @Autowired
    @Qualifier("accountServiceImpl")
    private AccountService accountService;

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody AccountCreateResponse create(final @RequestBody AccountCreateRequest accountCreateRequest) {
        return accountService.create(accountCreateRequest);
    }

    @Override
    @RequestMapping(value = "/secondary", method = RequestMethod.POST)
    public @ResponseBody AccountCreateResponse create(final @RequestBody SecondaryAccountCreateRequest secondaryAccountCreateRequest) {
        return accountService.create(secondaryAccountCreateRequest);
    }

    @Override
    @RequestMapping(value = "/{accountId}/customer/{customerId}", method = RequestMethod.GET)
    public @ResponseBody AccountsInfo get(
            final @PathVariable String accountId,
            final @PathVariable String customerId) {
        return accountService.get(accountId, customerId);
    }

    @Override
    public void deleteAll() {
        accountService.deleteAll();
    }
}
