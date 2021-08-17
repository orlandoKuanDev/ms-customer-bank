package com.bootcamp.mscustomer.controllers;

import com.bootcamp.mscustomer.models.entities.CustomerType;
import com.bootcamp.mscustomer.services.ICustomerTypeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer/type")
@Slf4j(topic = "CUSTOMER_TYPE_CONTROLLER")
public class CustomerTypeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerTypeController.class);

    @Autowired
    private ICustomerTypeService service;

    @GetMapping
    public Flux<CustomerType> findAll() {
        LOGGER.info("findAll");
        return service.findAll();
    }

    @PostMapping
    public Mono<ResponseEntity<CustomerType>> newCustomerType(@RequestBody CustomerType customerType){
        return service.save(customerType)
                .map(c -> ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(c));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomerType(@PathVariable String id){

        return service.findById(id).flatMap(customerType -> service.delete(customerType))
                .map(c -> ResponseEntity
                        .noContent().build());
    }
}