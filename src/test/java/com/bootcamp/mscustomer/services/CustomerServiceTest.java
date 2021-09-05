package com.bootcamp.mscustomer.services;

import com.bootcamp.mscustomer.Exception.EntityNotFoundException;
import com.bootcamp.mscustomer.data.DataProvider;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.repositories.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    ICustomerRepository repository;
    CustomerService mockCustomerService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ICustomerRepository.class);
        mockCustomerService = new CustomerService(repository);
    }

    @Test
    void findByName() {
        Customer customerRequest = DataProvider.CustomerRequest();
        Mockito.when(repository.findByName(customerRequest.getName()))
                .thenReturn(Mono.just(customerRequest));

        Mono<Customer> customerName = mockCustomerService.findByName(customerRequest.getName());

        StepVerifier.create(customerName)
                .expectNext(customerRequest)
                .verifyComplete();
    }
    @Test
    void findByName_EntityNotFoundException() {
        Customer customerRequest = DataProvider.CustomerRequest();

        Mockito.when(repository.findByName(customerRequest.getName()))
                .thenReturn(Mono.error(() -> new EntityNotFoundException("entity.customer.notNamePresent")));

        Mono<Customer> customerName = mockCustomerService.findByName(customerRequest.getName());

        StepVerifier.create(customerName)
                .expectErrorMatches(new EntityNotFoundException("entity.customer.notNamePresent")::equals)
                .verify();
    }
    @Test
    void findByCustomerIdentityNumber() {
        Customer customerRequest = DataProvider.CustomerRequest();
        Mockito.when(repository.findByCustomerIdentityNumber(customerRequest.getCustomerIdentityNumber()))
                .thenReturn(Mono.just(customerRequest));

        Mono<Customer> customerIdentityNumber = mockCustomerService.findByCustomerIdentityNumber(customerRequest.getCustomerIdentityNumber());

        StepVerifier.create(customerIdentityNumber)
                .expectNext(customerRequest)
                .verifyComplete();
    }
    @Test
    void findByCustomerIdentityNumber_EntityNotFoundException() {
        Customer customerRequest = DataProvider.CustomerRequest();

        Mockito.when(repository.findByCustomerIdentityNumber(customerRequest.getCustomerIdentityNumber()))
                .thenReturn(Mono.error(() -> new EntityNotFoundException("entity.customer.notIdentityPresent")));

        Mono<Customer> customerIdentityNumber = mockCustomerService.findByCustomerIdentityNumber(customerRequest.getCustomerIdentityNumber());

        StepVerifier.create(customerIdentityNumber)
                .expectErrorMatches(new EntityNotFoundException("entity.customer.notIdentityPresent")::equals)
                .verify();
    }
}