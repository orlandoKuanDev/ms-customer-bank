package com.bootcamp.mscustomer.services;

import com.bootcamp.mscustomer.Exception.EntityNotFoundException;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.repositories.ICustomerRepository;
import com.bootcamp.mscustomer.repositories.IRepository;
import com.bootcamp.mscustomer.util.CustomMessage;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j(topic = "CUSTOMER_SERVICE")
public class CustomerService extends BaseService<Customer, String> implements ICustomerService{
    static final String CIRCUIT = "customerServiceCircuitBreaker";
    private final ICustomerRepository customerRepository;
    private final CustomMessage customMessage;
    @Autowired
    public CustomerService(ICustomerRepository customerRepository, CustomMessage customMessage) {
        this.customerRepository = customerRepository;
        this.customMessage = customMessage;
    }

    @Override
    protected IRepository<Customer, String> getRepository() {
        return customerRepository;
    }

    @Override
    public Mono<Customer> findByName(String name) {
        Mono<Customer> customer = customerRepository.findByName(name);
        return customer.hasElement().flatMap(customerBD -> {
            if (Boolean.TRUE.equals(customerBD)){
                return customer;
            }else {
                return Mono.error(() -> new EntityNotFoundException(customMessage.getLocalMessage("entity.customer.notNamePresent")));
            }
        });
    }

    @Override
    @CircuitBreaker(name = CIRCUIT, fallbackMethod = "customerFallback")
    public Mono<Customer> findByCustomerIdentityNumber(String customerIdentityName) {
        Mono<Customer> customer = customerRepository.findByCustomerIdentityNumber(customerIdentityName);
        return customer.hasElement().flatMap(customerBD -> {
            if (Boolean.TRUE.equals(customerBD)){
                return customer;
            }else {
                return Mono.error(() -> new EntityNotFoundException(customMessage.getLocalMessage("entity.customer.notIdentityPresent")));
            }
        });
    }

    public Mono<Customer> customerFallback(String customerIdentityNumber, Exception ex) {

        return Mono.just(Customer
                .builder()
                .customerIdentityNumber(customerIdentityNumber)
                .name(ex.getMessage())
                .build());
    }
}
