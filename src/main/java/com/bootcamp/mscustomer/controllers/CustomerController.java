package com.bootcamp.mscustomer.controllers;

import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.models.entities.CustomerType;
import com.bootcamp.mscustomer.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{customer}")
    public Mono<ResponseEntity<Customer>> findByCustomer(@PathVariable("customer") String name) {
        LOGGER.info("findByCustomer: customerName={}", name);
        return customerService.findByName(name)
                .map(saveCustomer -> ResponseEntity.ok(saveCustomer))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Customer> findAll() {
        LOGGER.info("findAll");
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Customer>> findById(@PathVariable("id") String id) {
        LOGGER.info("findById: id={}", id);
        return customerService.findById(id)
                .map(saveCustomer -> ResponseEntity.ok(saveCustomer))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Customer> create(@Valid @RequestBody Customer customer) {
        LOGGER.info("create: {}", customer);
        return customerService.save(customer);
    }
    
    public Mono<ResponseEntity<Customer>> update(@PathVariable(value = "id") String id,
                                 @Valid @RequestBody Customer customer) {
        LOGGER.info("update: {}", customer);
        return customerService.findById(id)
                .flatMap(existCustomer -> {
                    existCustomer.setName(customer.getName());
                    existCustomer.setCode(customer.getCode());
                    existCustomer.setIban(customer.getIban());
                    existCustomer.setCustomerType(CustomerType.builder()
                                    .name(customer.getCustomerType().getName())
                            .build());
                    existCustomer.setPhone(customer.getPhone());
                    existCustomer.setAddress(customer.getAddress());
                    existCustomer.setSurname(customer.getSurname());
                    return customerService.save(existCustomer);
                }).map(updateCustomer -> new ResponseEntity<>(updateCustomer, HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}