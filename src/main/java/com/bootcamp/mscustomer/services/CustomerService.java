package com.bootcamp.mscustomer.services;

import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService implements ICustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Customer> update(String id, Customer customer) {
        return customerRepository.findById(id)
                .flatMap(c -> {
                    c.setId(id);
                    return save(c);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> delete(Customer customer) {
        return customerRepository.delete(customer);
    }
}
