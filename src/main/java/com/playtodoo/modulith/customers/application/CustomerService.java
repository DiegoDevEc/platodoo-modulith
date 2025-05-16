package com.playtodoo.modulith.customers.application;

import com.playtodoo.modulith.customers.validation.CreateCustomerDto;

import java.util.UUID;

public interface CustomerService {
    CreateCustomerDto findCustomerById(UUID customerId);
    CreateCustomerDto saveCustomer(CreateCustomerDto customerDto);
}
