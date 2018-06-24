package com.example.account.management.accountmanager.dao;

import com.example.account.management.accountmanager.model.Account;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class AccountDao {

    private Set<Account> accounts = new HashSet<>();

    public String save(Account account) {
        accounts.add(account);
        return account.getNumber();
    }

    public Optional<Account> get(final String accountId) {
        return accounts.stream().filter(account -> account.getNumber().equals(accountId)).findFirst();
    }

    public List<Account> getByCustomerId(final String accountId,
                                         final String customerId) {
        return accounts.stream()
                       .filter(account -> account.getNumber().equals(accountId) && account.getCustomerId().equals(customerId))
                       .collect(Collectors.toList());
    }

    public void deleteAll() {
        accounts.clear();
    }
}
