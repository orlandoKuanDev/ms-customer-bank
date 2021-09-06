package com.bootcamp.mscustomer.services;

import com.bootcamp.mscustomer.Exception.EntityNotFoundException;
import com.bootcamp.mscustomer.data.DataProvider;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.repositories.ICustomerRepository;
import com.bootcamp.mscustomer.util.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    ICustomerRepository repository;
    CustomerService mockCustomerService;
    CustomMessage customMessage;
    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ICustomerRepository.class);
        mockCustomerService = new CustomerService(repository, customMessage);
    }

    @Test
    void findByName() {
        Customer customerRequest = DataProvider.CustomerRequest();
        Mockito.when(repository.findByName(customerRequest.getName()))
                .thenReturn(Mono.just(customerRequest));

        Mono<Customer> customerName = mockCustomerService
                .findByName(customerRequest.getName());

        StepVerifier.create(customerName)
                .expectNext(customerRequest)
                .verifyComplete();
    }
    @Test
    void findByName_EntityNotFoundException() {
        Customer customerRequest = DataProvider.CustomerRequest();
        customerRequest.setCustomerIdentityNumber("ORLANDO");
        Mockito.when(repository.findByName(customerRequest.getName()))
                .thenReturn(Mono.error(() -> new EntityNotFoundException(customMessage.getLocalMessage("entity.customer.notNamePresent"))));

        Mono<Customer> customerName = mockCustomerService.findByName(customerRequest.getName());

        StepVerifier.create(customerName)
                .expectErrorMatches(new EntityNotFoundException(customMessage.getLocalMessage("entity.customer.notNamePresent"))::equals)
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
        customerRequest.setCustomerIdentityNumber("70000000");
        Mockito.when(repository.findByCustomerIdentityNumber(customerRequest.getCustomerIdentityNumber()))
                .thenReturn(Mono.error(() -> new EntityNotFoundException(customMessage.getLocalMessage("entity.customer.notIdentityPresent"))));

        Mono<Customer> customerIdentityNumber = mockCustomerService.findByCustomerIdentityNumber(customerRequest.getCustomerIdentityNumber());

        StepVerifier.create(customerIdentityNumber)
                .expectErrorMatches(new EntityNotFoundException(customMessage.getLocalMessage("entity.customer.notIdentityPresent"))::equals)
                .verify();
    }
}