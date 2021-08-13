package com.bootcamp.mscustomer.controllers;

import com.bootcamp.mscustomer.common.ApiResponse;
import com.bootcamp.mscustomer.models.dto.CustomerDTO;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.services.Impl.CustomerServiceImpl;
import com.bootcamp.mscustomer.services.Impl.CustomerTypeServiceImpl;
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
    private CustomerServiceImpl service;

    @Autowired
    private CustomerTypeServiceImpl typeService;

    @GetMapping
    public Flux<Customer> findAll() {
        LOGGER.info("findAll");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Customer>> findById(@PathVariable("id") String id) {
        LOGGER.info("findById: id={}", id);
        return service.findById(id)
                .map(saveCustomer -> ResponseEntity.ok(saveCustomer))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Customer>> create(@RequestParam String code , @RequestBody Mono<Customer> request) {
        LOGGER.info("create: {}", request);

        return request
                .flatMap(customerCreate -> typeService.findByCode(code)
                .flatMap(type -> {
                    if(customerCreate.getCustomerType() != null && !code.equals(customerCreate.getCustomerType().getCode())){
                        return Mono.empty();
                    } else {
                        customerCreate.setCustomerType(type);
                        return service.save(customerCreate);
                    }
                }))
                .map(customerCreate -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerCreate))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST)));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ApiResponse<Object>> update(@PathVariable(value = "id") String id,
                                 @Valid @RequestBody Customer customer) {
        LOGGER.info("update: {}", customer);
        return service.update(id, customer)
                .flatMap(customerUpdate -> Mono.just(
                    ApiResponse.builder()
                    .message("Customer update successfully")
                    .status(HttpStatus.OK)
                    .data(customerUpdate)
                    .build()));
    }

    @GetMapping("/findCustomerCredit/{customerIdentityNumber}")
    public Mono<ResponseEntity<CustomerDTO>> findCustomerCredit(@PathVariable String customerIdentityNumber){
        LOGGER.info("findByCustomerIdentityNumber: customerIdentityNumber={}", customerIdentityNumber);
        return service.findByCustomerIdentityNumber(customerIdentityNumber)
                .map(saveCustomer -> ResponseEntity.ok(saveCustomer))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomerType(@PathVariable String id){

        return service.findById(id).flatMap(customerType -> service.delete(customerType))
                .map(c -> ResponseEntity
                .noContent().build());
    }
}