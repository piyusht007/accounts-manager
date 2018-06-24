package com.example.account.management.accountmanager.dao;

import com.example.account.management.accountmanager.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDao {
    private final List<Customer> customers = new ArrayList<>();

    public String save(Customer customer) {
        customers.add(customer);
        return customer.getId();
    }

    public Optional<Customer> getById(final String customerId) {
        return customers.stream().filter(customer -> customer.getId().equals(customerId)).findFirst();
    }

    public void deleteAll() {
        customers.clear();
    }
}