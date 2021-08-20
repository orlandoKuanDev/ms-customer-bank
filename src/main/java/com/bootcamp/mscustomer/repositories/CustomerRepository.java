package com.bootcamp.mscustomer.repositories;

import com.bootcamp.mscustomer.models.dto.CustomerDTO;
import com.bootcamp.mscustomer.models.entities.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Customer repository.
 */
@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {
    Mono<Customer> findById(String id);

    /**
     * Find by name mono.
     *
     * @param name the name
     * @return the mono
     */
    Mono<Customer> findByName(String name);

    /**
     * Find by customer identity number mono.
     *
     * @param customerIdentityNumber the customer identity number
     * @return the mono
     */
    Mono<Customer> findByCustomerIdentityNumber(String customerIdentityNumber);
}
