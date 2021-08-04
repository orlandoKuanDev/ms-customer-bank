package com.bootcamp.mscustomer.controllers;

import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.models.entities.CustomerType;
import com.bootcamp.mscustomer.repositories.CustomerTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customertype")
@Slf4j(topic = "CUSTOMER_TYPE_CONTROLLER")
public class CustomerTypeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerTypeController.class);

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @GetMapping
    public Flux<CustomerType> findAll() {
        LOGGER.info("findAll");
        return customerTypeRepository.findAll();
    }
}