package com.bootcamp.mscustomer.controllers;

import com.bootcamp.mscustomer.common.ApiResponse;
import com.bootcamp.mscustomer.models.dto.CustomerDTO;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.services.Impl.CustomerServiceImpl;
import com.bootcamp.mscustomer.services.Impl.CustomerTypeServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * The type Customer controller.
 */
@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final String CIRCUIT = "customerServiceCircuitBreaker";

    @Autowired
    private CustomerServiceImpl service;

    @Autowired
    private CustomerTypeServiceImpl typeService;

    /**
     * Find all flux.
     *
     * @return the flux
     */
    @GetMapping
    public Flux<Customer> findAll() {
        LOGGER.info("findAll");
        return service.findAll();
    }

    /**
     * Find by id mono.
     *
     * @param id the id
     * @return the mono
     */
    @CircuitBreaker(name = CIRCUIT, fallbackMethod = "fallback")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Customer>> findById(@PathVariable("id") String id) {
        LOGGER.info("findById: id={}", id);
        return service.findById(id)
                .map(saveCustomer -> ResponseEntity.ok(saveCustomer))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create mono.
     *
     * @param code    the code
     * @param request the request
     * @return the mono
     */
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

    /**
     * Update mono.
     *
     * @param id       the id
     * @param customer the customer
     * @return the mono
     */
    @PutMapping(value = "/{id}")
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

    /**
     * Fallback mono.
     *
     * @return the mono
     */
    public Mono<ResponseEntity<Customer>> fallback(){
        LOGGER.info("Entra al fallBack");
        return Mono.just(Customer.builder().build())
                .map(saveCustomer -> ResponseEntity.ok(saveCustomer))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Update cards mono.
     *
     * @param id       the id
     * @param customer the customer
     * @return the mono
     */
    @PutMapping(value = "/cards/{id}")
    public Mono<ApiResponse<Object>> updateCards(@PathVariable(value = "id") String id,
                                             @RequestBody Customer customer) {
        LOGGER.info("update: {}", customer);
        return service.updateCard(id, customer)
                .flatMap(customerUpdate -> Mono.just(
                        ApiResponse.builder()
                                .message("Customer update successfully")
                                .status(HttpStatus.OK)
                                .data(customerUpdate)
                                .build()));
    }

    /**
     * Find customer credit mono.
     *
     * @param customerIdentityNumber the customer identity number
     * @return the mono
     */
    @GetMapping("/findCustomerCredit/{customerIdentityNumber}")
    public Mono<ResponseEntity<Customer>> findCustomerCredit(@PathVariable String customerIdentityNumber){
        LOGGER.info("findByCustomerIdentityNumber: customerIdentityNumber={}", customerIdentityNumber);
        return service.findByCustomerIdentityNumber(customerIdentityNumber)
                .map(saveCustomer -> ResponseEntity.ok(saveCustomer))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete customer type mono.
     *
     * @param id the id
     * @return the mono
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomerType(@PathVariable String id){

        return service.findById(id).flatMap(customerType -> service.delete(customerType))
                .map(c -> ResponseEntity
                .noContent().build());
    }
}