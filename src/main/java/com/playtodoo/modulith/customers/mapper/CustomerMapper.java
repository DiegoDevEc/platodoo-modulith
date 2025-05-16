package com.playtodoo.modulith.customers.mapper;

import com.playtodoo.modulith.customers.validation.CreateCustomerDto;
import com.playtodoo.modulith.customers.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CreateCustomerDto toCustomerDto(Customer customer);

    Customer toCustomer(CreateCustomerDto customerDto);
}
