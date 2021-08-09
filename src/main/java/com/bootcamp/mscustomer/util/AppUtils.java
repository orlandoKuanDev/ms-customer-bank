package com.bootcamp.mscustomer.util;

import com.bootcamp.mscustomer.models.dto.CustomerDTO;
import com.bootcamp.mscustomer.models.entities.Customer;

public class AppUtils {
    public static CustomerDTO entityToDto(Customer customer) {
        return null;
        /*return CustomerDTO.builder()
                .code(customer.getCode())
                .iban(customer.getIban())
                .name(customer.getName())
                .phone(customer.getPhone())
                .surname(customer.getSurname())
                .address(customer.getAddress())
                .build();*/
    }

    public static Customer dtoToEntity(CustomerDTO customerDTO) {
        return null;
        /*return Customer.builder()
                .code(customerDTO.getCode())
                .iban(customerDTO.getIban())
                .name(customerDTO.getName())
                .phone(customerDTO.getPhone())
                .surname(customerDTO.getSurname())
                .address(customerDTO.getAddress())
                .build();*/
    }
}
