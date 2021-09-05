package com.bootcamp.mscustomer.services;

import com.bootcamp.mscustomer.Exception.EntityNotFoundException;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.repositories.ICustomerRepository;
import com.bootcamp.mscustomer.repositories.IRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j(topic = "CUSTOMER_SERVICE")
public class CustomerService extends BaseService<Customer, String> implements ICustomerService{
    private final ICustomerRepository customerRepository;

    @Autowired
    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    protected IRepository<Customer, String> getRepository() {
        return customerRepository;
    }

    @Override
    public Mono<Customer> findByName(String name) {
        Mono<Customer> customer = customerRepository.findByName(name);
        return customer.hasElement().flatMap(customerBD -> {
            if (Boolean.TRUE.equals(customerBD)){
                return customer;
            }else {
                return Mono.error(() -> new EntityNotFoundException("entity.customer.notNamePresent"));
            }
        });
        //return customerRepository.findByName(name);
    }

    @Override
    public Mono<Customer> findByCustomerIdentityNumber(String customerIdentityName) {
        Mono<Customer> customer = customerRepository.findByCustomerIdentityNumber(customerIdentityName);
        return customer.hasElement().flatMap(customerBD -> {
            if (Boolean.TRUE.equals(customerBD)){
                return customer;
            }else {
                return Mono.error(() -> new EntityNotFoundException("entity.customer.notIdentityPresent"));
            }
        });
        //return customerRepository.findByCustomerIdentityNumber(customerIdentityName);
    }
}
