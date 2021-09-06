package com.bootcamp.mscustomer.handler;

import com.bootcamp.mscustomer.MsCustomerApplication;
import com.bootcamp.mscustomer.config.RouterConfig;
import com.bootcamp.mscustomer.data.DataProvider;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.repositories.ICustomerRepository;
import com.bootcamp.mscustomer.services.CustomerService;
import com.bootcamp.mscustomer.util.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MsCustomerApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CustomerHandlerTest {
    @Autowired
    RouterConfig routes;
    ICustomerRepository repository;
    CustomerService customerService;
    CustomerHandler customerHandler;
    WebTestClient webTestClient;
    CustomMessage customMessage;
    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ICustomerRepository.class);
        customerHandler = Mockito.mock(CustomerHandler.class);
        webTestClient = WebTestClient
                .bindToRouterFunction(routes.rutas(customerHandler))
                .build();
    }
    @Test
    void findAll() {
        Customer customer1 = DataProvider.CustomerRequest();
        Customer customer2 = DataProvider.CustomerRequest2();

        List<Customer> customers = Arrays.asList(
                customer1,
                customer2);

        Mockito.when(repository.findAll())
                .thenReturn(Flux.just(customer1, customer2));

        Flux<Customer> productFlux = Flux.fromIterable(customers);
        given(repository.findAll()).willReturn(productFlux);

        webTestClient
                .get()
                .uri("/product")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Customer.class)
                .isEqualTo(customers);
    }
}