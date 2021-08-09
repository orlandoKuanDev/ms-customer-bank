package com.bootcamp.mscustomer.services;

import com.bootcamp.mscustomer.common.ApiResponse;
import com.bootcamp.mscustomer.models.dto.CustomerDTO;
import com.bootcamp.mscustomer.models.entities.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService extends IBaseService<Customer, String> {
    Mono<Customer> findByName(String name);
    Mono<Customer> findByCustomerIdentityNumber(String customerIdentityName);
}
