package com.bootcamp.mscustomer.services;

import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.models.entities.CustomerType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerTypeService {

    public Flux<CustomerType> findAll();

    public Mono<CustomerType> findById(String id);

    public Mono<CustomerType> findByCode(String code);

    public Mono<CustomerType> save(CustomerType customerType);

    public Mono<Void> delete(CustomerType customer);

}
