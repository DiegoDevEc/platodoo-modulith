package com.playtodoo.modulith.customers.presentation;

import com.playtodoo.modulith.customers.application.CustomerService;
import com.playtodoo.modulith.customers.domain.Customer;
import com.playtodoo.modulith.customers.validation.CreateCustomerDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public CreateCustomerDto create(@RequestBody @Valid CreateCustomerDto dto) {
        return service.saveCustomer(dto);
    }

    @GetMapping("/{id}")
    public CreateCustomerDto getById(@PathVariable UUID id) {
        return service.findCustomerById(id);
    }

}