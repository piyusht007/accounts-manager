package com.example.account.management.accountmanager.api.service;

import com.example.account.management.accountmanager.api.CustomerCreateRequest;
import com.example.account.management.accountmanager.model.Customer;

public interface CustomerService {
    String create(CustomerCreateRequest customerCreateRequest);

    Customer get(final String id);

    void deleteAll();
}
