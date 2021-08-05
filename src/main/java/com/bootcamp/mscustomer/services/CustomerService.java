package com.bootcamp.mscustomer.services;

import com.bootcamp.mscustomer.Exception.ValidationService;
import com.bootcamp.mscustomer.common.ApiResponse;
import com.bootcamp.mscustomer.models.dto.CustomerDTO;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.models.entities.CustomerType;
import com.bootcamp.mscustomer.repositories.CustomerRepository;
import com.bootcamp.mscustomer.repositories.CustomerTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j(topic = "CUSTOMER_SERVICE")
public class CustomerService implements ICustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> findById(String id) {
        return customerRepository.findById(id);
    }
    @Override
    public Mono<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Customer> update(String id, Customer customer) {
        validationService.validate(customer);
        log.info("CUSTOMER_DATA: {}", customer);
        log.info("CUSTOMER_DATA_ID: {}", id);
        return customerRepository.findById(id)
                .flatMap(existCustomer -> {
                    existCustomer.setName(customer.getName());
                    existCustomer.setCode(customer.getCode());
                    existCustomer.setIban(customer.getIban());
                    existCustomer.setPhone(customer.getPhone());
                    existCustomer.setAddress(customer.getAddress());
                    existCustomer.setSurname(customer.getSurname());
                    existCustomer.setCustomerType(customer.getCustomerType());
                    return customerRepository.save(existCustomer);
                }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> delete(Customer customer) {
        return customerRepository.delete(customer);
    }
}
