package com.bootcamp.mscustomer.controllers;

import com.bootcamp.mscustomer.common.ApiResponse;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@Slf4j(topic = "CUSTOMER_CONTROLLER")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

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

    @PostMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ApiResponse<Object>> create(@Valid @RequestBody Customer request) {
        LOGGER.info("create: {}", request);
        return customerService.save(request).flatMap(customerCreate -> Mono.just(
                ApiResponse.builder()
                .message("Customer created successfully")
                .status(HttpStatus.CREATED)
                .data(customerCreate)
                .build()));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ApiResponse<Object>> update(@PathVariable(value = "id") String id,
                                 @Valid @RequestBody Customer customer) {
        LOGGER.info("update: {}", customer);
        return customerService.update(id, customer)
                .flatMap(customerUpdate -> Mono.just(
                    ApiResponse.builder()
                    .message("Customer update successfully")
                    .status(HttpStatus.OK)
                    .data(customerUpdate)
                    .build()));
    }
}