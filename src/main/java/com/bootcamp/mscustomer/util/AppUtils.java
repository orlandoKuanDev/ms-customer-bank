package com.bootcamp.mscustomer.util;

import com.bootcamp.mscustomer.models.dto.CustomerDTO;
import com.bootcamp.mscustomer.models.entities.Customer;

/**
 * The type App utils.
 */
public class AppUtils {
    /**
     * Entity to dto customer dto.
     *
     * @param customer the customer
     * @return the customer dto
     */
    public static CustomerDTO entityToDto(Customer customer) {
        return CustomerDTO.builder()
                .name(customer.getName())
                .customerIdentityType(customer.getCustomerIdentityType())
                .customerIdentityNumber(customer.getCustomerIdentityNumber())
                .build();
    }

    /**
     * Dto to entity customer.
     *
     * @param customerDTO the customer dto
     * @return the customer
     */
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
