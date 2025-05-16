package com.playtodoo.modulith.customers.application;

import com.playtodoo.modulith.customers.exception.CustomerNotFoundException;
import com.playtodoo.modulith.customers.validation.CreateCustomerDto;
import com.playtodoo.modulith.customers.mapper.CustomerMapper;
import com.playtodoo.modulith.customers.domain.Customer;
import com.playtodoo.modulith.customers.infrastructure.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Override
    public CreateCustomerDto saveCustomer(CreateCustomerDto customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        return customerMapper.toCustomerDto(customerRepository.save(customer));
    }

    @Override
    public CreateCustomerDto findCustomerById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));

        return customerMapper.toCustomerDto(customer);
    }
}
