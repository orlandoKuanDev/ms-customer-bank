package com.bootcamp.mscustomer.controllers;

import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{customer}")
    public Mono<Customer> findByCustomer(@PathVariable("customer") String customerId) {
        LOGGER.info("findByCustomer: customerId={}", customerId);
        return customerService.findById(customerId);
    }

    @GetMapping
    public Flux<Customer> findAll() {
        LOGGER.info("findAll");
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Customer> findById(@PathVariable("id") String id) {
        LOGGER.info("findById: id={}", id);
        return customerService.findById(id);
    }

    @PostMapping
    public Mono<Customer> create(@RequestBody Customer customer) {
        LOGGER.info("create: {}", customer);
        return customerService.save(customer);
    }
}