package com.bootcamp.mscustomer.services;

import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.models.entities.CustomerType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Customer type service.
 */
public interface ICustomerTypeService {

    /**
     * Find all flux.
     *
     * @return the flux
     */
    public Flux<CustomerType> findAll();

    /**
     * Find by id mono.
     *
     * @param id the id
     * @return the mono
     */
    public Mono<CustomerType> findById(String id);

    /**
     * Find by code mono.
     *
     * @param code the code
     * @return the mono
     */
    public Mono<CustomerType> findByCode(String code);

    /**
     * Save mono.
     *
     * @param customerType the customer type
     * @return the mono
     */
    public Mono<CustomerType> save(CustomerType customerType);

    /**
     * Delete mono.
     *
     * @param customer the customer
     * @return the mono
     */
    public Mono<Void> delete(CustomerType customer);

}
