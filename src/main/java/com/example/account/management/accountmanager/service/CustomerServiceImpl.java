package com.example.account.management.accountmanager.service;

import com.example.account.management.accountmanager.api.CustomerCreateRequest;
import com.example.account.management.accountmanager.api.Errors;
import com.example.account.management.accountmanager.api.service.CustomerService;
import com.example.account.management.accountmanager.dao.CustomerDao;
import com.example.account.management.accountmanager.model.Customer;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public String create(final CustomerCreateRequest customerCreateRequest) {
        validateCustomerCreateRequest(customerCreateRequest);

        final Customer customer = mapToCustomer(customerCreateRequest);
        return customerDao.save(customer);
    }

    @Override
    public Customer get(final String id) {
        return customerDao.getById(id)
                          .orElseThrow(() -> new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND.getMessage() + id));
    }

    private Customer mapToCustomer(final CustomerCreateRequest customerCreateRequest) {
        final String customerId = String.valueOf(Math.abs(new Random().nextLong()));
        return new Customer(customerId, customerCreateRequest.getFirstName(), customerCreateRequest.getLastName());
    }

    private void validateCustomerCreateRequest(final CustomerCreateRequest customerCreateRequest) {
        Preconditions.checkArgument(customerCreateRequest != null, "Customer request cannot be null.");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(customerCreateRequest.getFirstName()),
                                    "Customer first name cannot be null/empty.");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(customerCreateRequest.getLastName()),
                                    "Customer last name cannot be null/empty.");
    }

    @Override
    public void deleteAll() {
        customerDao.deleteAll();
    }
}
