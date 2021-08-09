package com.bootcamp.mscustomer.repositories;

import com.bootcamp.mscustomer.models.entities.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ICustomerRepository extends IRepository<Customer, String> {
    Mono<Customer> findByName(String name);
    Mono<Customer> findByCustomerIdentityNumber(String customerIdentityName);
}
