package com.bootcamp.mscustomer.repositories;

import com.bootcamp.mscustomer.models.dto.CustomerDTO;
import com.bootcamp.mscustomer.models.entities.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {
    Mono<Customer> findById(String id);
    Mono<Customer> findByName(String name);
    Mono<Customer> findByCustomerIdentityNumber(String customerIdentityNumber);
}
