package com.example.account.management.accountmanager;

import com.example.account.management.accountmanager.api.*;
import com.example.account.management.accountmanager.api.service.AccountService;
import com.example.account.management.accountmanager.api.service.CustomerService;
import com.example.account.management.accountmanager.api.service.TransactionsService;
import com.example.account.management.accountmanager.config.AppConfig;
import com.example.account.management.accountmanager.dao.AccountDao;
import com.example.account.management.accountmanager.service.AccountsNotFoundException;
import com.example.account.management.accountmanager.service.CustomerNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class AccountServiceTest {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    @Qualifier("accountServiceImpl")
    private AccountService accountService;

    @Test
    public void testCreateAccountWithNoInitialCreditSuccess() {
        // Given
        final String firstName = "Piyush";
        final String lastName = "Tiwari";
        final AccountCreateResponse accountCreateResponse = createPrimaryAccount(firstName, lastName, null);

        // When
        final AccountsInfo accountsInfo = accountService.get(accountCreateResponse.getCustomerId());
        final AccountSummary actualAccountSummary = accountsInfo.getAccountSummaries().stream().findFirst().get();

        // Then
        Assert.assertEquals(Double.valueOf(0), actualAccountSummary.getBalance());
        Assert.assertEquals(firstName, actualAccountSummary.getFirstName());
        Assert.assertEquals(lastName, actualAccountSummary.getLastName());
    }

    @Test
    public void testCreateAccountWithInitialCreditSuccess() {
        // Given
        final String firstName = "Piyush";
        final String lastName = "Tiwari";
        final String initialCredit = "1000";
        final AccountCreateResponse accountCreateResponse = createPrimaryAccount(firstName, lastName, initialCredit);

        // When
        sleep(1);

        final AccountsInfo accountsInfo = accountService.get(accountCreateResponse.getCustomerId());
        final AccountSummary actualAccountSummary = accountsInfo.getAccountSummaries().stream().findFirst().get();

        // Then
        Assert.assertEquals(Double.valueOf(1000), actualAccountSummary.getBalance());
        Assert.assertEquals(firstName, actualAccountSummary.getFirstName());
        Assert.assertEquals(lastName, actualAccountSummary.getLastName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAccountMalformedRequestExpectedExceptionWhenFirstNameNull() {
        createPrimaryAccount(null, "Jacob", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAccountMalformedRequestExpectedExceptionWhenFirstNameEmpty() {
        createPrimaryAccount("", "Jacob", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAccountMalformedRequestExpectedExceptionWhenLastNameNull() {
        createPrimaryAccount("Sam", null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAccountMalformedRequestExpectedExceptionWhenLastNameEmpty() {
        createPrimaryAccount("Sam", "", null);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void testGetAccountDetailsExpectedExceptionWhenInvalidCustomerIdIsProvided() {
        accountService.get(null);
    }

    @Test
    public void testSecondaryAccountCreationWithNoInitialCredit() {
        // Given
        final String firstName = "Piyush";
        final String lastName = "Tiwari";
        final String initialCredit = "1000";
        final AccountCreateResponse accountCreateResponse = createPrimaryAccount(firstName, lastName, initialCredit);
        final SecondaryAccountCreateRequest secondaryAccountCreateRequest = createSecondaryAccountCreateRequest(
                accountCreateResponse.getCustomerId());

        // When
        final AccountCreateResponse secondaryAccountCreateResponse = accountService.create(secondaryAccountCreateRequest);

        sleep(1);

        final AccountsInfo accountsInfo = accountService.get(secondaryAccountCreateResponse.getCustomerId());
        final List<AccountSummary> accountSummaries = accountsInfo.getAccountSummaries();

        // Then
        final AccountSummary primaryAccountSummary = getAccountSummary(accountCreateResponse, accountSummaries);
        final AccountSummary secondaryAccountSummary = getAccountSummary(secondaryAccountCreateResponse, accountSummaries);

        Assert.assertEquals(Double.valueOf(1000), primaryAccountSummary.getBalance());
        Assert.assertEquals(firstName, primaryAccountSummary.getFirstName());
        Assert.assertEquals(lastName, primaryAccountSummary.getLastName());

        Assert.assertEquals(Double.valueOf(0), secondaryAccountSummary.getBalance());
        Assert.assertEquals(firstName, secondaryAccountSummary.getFirstName());
        Assert.assertEquals(lastName, secondaryAccountSummary.getLastName());
    }

    private AccountSummary getAccountSummary(final AccountCreateResponse accountCreateResponse,
                                             final List<AccountSummary> accountSummaries) {
        return accountSummaries.stream()
                               .filter(accountSummary -> accountSummary.getAccountId().equals(accountCreateResponse.getAccountId()))
                               .findFirst()
                               .get();
    }

    @Test
    public void testSecondaryAccountCreationWithInitialCredit() {
        // Given
        final String firstName = "Piyush";
        final String lastName = "Tiwari";
        final String initialCredit = "1000";
        final AccountCreateResponse accountCreateResponse = createPrimaryAccount(firstName, lastName, initialCredit);
        final SecondaryAccountCreateRequest secondaryAccountCreateRequest = createSecondaryAccountCreateRequest(
                accountCreateResponse.getCustomerId());

        secondaryAccountCreateRequest.setInitialCredit("1000");

        // When
        final AccountCreateResponse secondaryAccountCreateResponse = accountService.create(secondaryAccountCreateRequest);

        sleep(1);

        final AccountsInfo accountsInfo = accountService.get(secondaryAccountCreateResponse.getCustomerId());
        final List<AccountSummary> accountSummaries = accountsInfo.getAccountSummaries();

        // Then
        final AccountSummary primaryAccountSummary = getAccountSummary(accountCreateResponse, accountSummaries);
        final AccountSummary secondaryAccountSummary = getAccountSummary(secondaryAccountCreateResponse, accountSummaries);

        Assert.assertEquals(Double.valueOf(1000), primaryAccountSummary.getBalance());
        Assert.assertEquals(firstName, primaryAccountSummary.getFirstName());
        Assert.assertEquals(lastName, primaryAccountSummary.getLastName());

        Assert.assertEquals(Double.valueOf(1000), secondaryAccountSummary.getBalance());
        Assert.assertEquals(firstName, secondaryAccountSummary.getFirstName());
        Assert.assertEquals(lastName, secondaryAccountSummary.getLastName());
    }

    private void sleep(final long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    private SecondaryAccountCreateRequest createSecondaryAccountCreateRequest(final String customerId) {
        return new SecondaryAccountCreateRequest(customerId);
    }

    private AccountCreateResponse createPrimaryAccount(final String firstName,
                                                       final String lastName,
                                                       final String initialCredit) {
        final AccountCreateRequest accountCreateRequest = new AccountCreateRequest(firstName, lastName);

        accountCreateRequest.setInitialCredit(initialCredit);
        return accountService.create(accountCreateRequest);
    }
}