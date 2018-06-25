package com.example.account.management.accountmanager.integration.tests;

import com.example.account.management.accountmanager.api.*;
import com.example.account.management.accountmanager.api.service.AccountService;
import com.example.account.management.accountmanager.api.service.CustomerService;
import com.example.account.management.accountmanager.api.service.TransactionsService;
import com.example.account.management.accountmanager.config.AppConfig;
import com.example.account.management.accountmanager.model.Transaction;
import com.example.account.management.accountmanager.model.Type;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = { AppConfig.class })
public class AccountSteps {

    @Autowired
    @Qualifier("accountServiceImpl")
    private AccountService accountService;

    @Autowired
    @Qualifier("transactionsServiceImpl")
    private TransactionsService transactionsService;

    @Autowired
    private CustomerService customerService;

    private static final Map<String, String> customerFirstNameToId = new HashMap<>();

    private AccountCreateResponse secondaryAccountCreateResponse = null;

    @Then("^delete all customers, accounts and transactions$")
    public void delete_all_loan_application_users_approval_hierarchy_tasks() {
        accountService.deleteAll();
        transactionsService.deleteAll();
        customerService.deleteAll();
    }

    @Given("^the following customers needs to open accounts$")
    public void the_following_approval_levels(List<CustomerLine> customerLines) {
        createAccounts(customerLines);
    }

    @When("^i submit the secondary current account creation request for: (.*) with initial credit amount: (.*)$")
    public void i_submit_the_secondary_current_account_creation_request_for_customer_name_with_initial_credit_amount(final String customerName,
                                                                                                                     final String initialCreditAmount) {

        final String customerId = customerFirstNameToId.get(customerName);
        final SecondaryAccountCreateRequest secondaryAccountCreateRequest = new SecondaryAccountCreateRequest(String.valueOf(
                customerId));

        secondaryAccountCreateRequest.setInitialCredit(initialCreditAmount);
        secondaryAccountCreateResponse = accountService.create(secondaryAccountCreateRequest);
    }

    @Then("^(.*) should get a new current account with opening balance (.*)$")
    public void customer_name_should_get_a_new_current_account_with_opening_balance(final String customerName,
                                                                                    final String expectedBalance) {

        sleep(1);
        final String customerId = customerFirstNameToId.get(customerName);
        final AccountsInfo accountsInfo = accountService.get(String.valueOf(customerId));

        Assert.assertNotNull(accountsInfo);

        final List<AccountSummary> accountSummaries = accountsInfo.getAccountSummaries();

        Assert.assertNotNull(accountSummaries);
        Assert.assertFalse(accountSummaries.isEmpty());

        final AccountSummary accountSummary = accountSummaries.stream()
                                                              .filter(as -> as.getAccountId()
                                                                              .equals(secondaryAccountCreateResponse.getAccountId()))
                                                              .findFirst()
                                                              .get();

        Assert.assertEquals(secondaryAccountCreateResponse.getAccountId(), accountSummary.getAccountId());
        Assert.assertEquals(secondaryAccountCreateResponse.getCustomerId(), accountSummary.getCustomerId());
        Assert.assertEquals(Double.valueOf(expectedBalance), accountSummary.getBalance());
        Assert.assertEquals(customerName, accountSummary.getFirstName());
        assertTransactions(expectedBalance, accountSummary.getTransactions());
    }

    private void assertTransactions(final String expectedBalance,
                                    final List<Transaction> transactions) {
        for( final Transaction transaction : transactions ) {
            Assert.assertEquals(secondaryAccountCreateResponse.getAccountId(), transaction.getAccountId());
            Assert.assertEquals(Double.valueOf(expectedBalance), transaction.getAmount());
            Assert.assertEquals(Type.CREDIT, transaction.getType());
        }
    }

    private void createAccounts(final List<CustomerLine> customerLines) {
        customerLines.forEach(customerLine -> {
            final AccountCreateRequest accountCreateRequest = new AccountCreateRequest(customerLine.firstName,
                                                                                       customerLine.lastName);

            final AccountCreateResponse accountCreateResponse = accountService.create(accountCreateRequest);
            customerFirstNameToId.put(customerLine.firstName, accountCreateResponse.getCustomerId());
        });
    }

    private void sleep(final long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
