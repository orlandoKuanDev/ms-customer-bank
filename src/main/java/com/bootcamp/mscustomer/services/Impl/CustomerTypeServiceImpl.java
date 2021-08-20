package com.bootcamp.mscustomer.services.Impl;

import com.bootcamp.mscustomer.models.entities.CustomerType;
import com.bootcamp.mscustomer.repositories.CustomerTypeRepository;
import com.bootcamp.mscustomer.services.ICustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Customer type service.
 */
@Service
public class CustomerTypeServiceImpl implements ICustomerTypeService {

    @Autowired
    private CustomerTypeRepository repository;

    @Override
    public Flux<CustomerType> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<CustomerType> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<CustomerType> findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public Mono<CustomerType> save(CustomerType customerType) {
        return repository.save(customerType);
    }

    @Override
    public Mono<Void> delete(CustomerType customerType) {
        return repository.delete(customerType);
    }
}
