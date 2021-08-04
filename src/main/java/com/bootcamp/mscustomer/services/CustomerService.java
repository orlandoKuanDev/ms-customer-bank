package com.bootcamp.mscustomer.services;

import com.bootcamp.mscustomer.common.ApiResponse;
import com.bootcamp.mscustomer.models.dto.CustomerDTO;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.models.entities.CustomerType;
import com.bootcamp.mscustomer.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j(topic = "CUSTOMER_SERVICE")
public class CustomerService implements ICustomerService{

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Mono<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Mono<Customer> save(Customer customerDTO) {
        return customerRepository.save(customerDTO);
    }

    @Override
    public Mono<ApiResponse<Object>> saveAll(Customer customerDTO) {
        return Mono.just(customerDTO)
                .flatMap(customerCreate -> {
                    Customer customer = new Customer();
                    customer.setName(customerCreate.getName());
                    customer.setSurname(customerCreate.getSurname());
                    customer.setCode(customerCreate.getCode());
                    customer.setIban(customerCreate.getIban());
                    customer.setPhone(customerCreate.getPhone());
                    customer.setAddress(customer.getAddress());
                    CustomerType customerType = new CustomerType();
                    customerType.setName(customer.getCustomerType().getName());
                    //BeanUtils.copyProperties(customer.getCustomerType(), customerType);
                    customer.setCustomerType(customerType);
                    return customerRepository.save(customer);
                }).map(customer -> ApiResponse.builder()
                        .status(HttpStatus.CREATED)
                        .message("Customer create successfully")
                        .data(customer)
                        .build());
    }



    @Override
    public Mono<Customer> update(String id, Customer customer) {
        return customerRepository.findById(id)
                .flatMap(existCustomer -> {
                    existCustomer.setName(customer.getName());
                    existCustomer.setCode(customer.getCode());
                    existCustomer.setIban(customer.getIban());
                    existCustomer.setPhone(customer.getPhone());
                    existCustomer.setAddress(customer.getAddress());
                    existCustomer.setSurname(customer.getSurname());
                    return customerRepository.save(existCustomer);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> delete(Customer customer) {
        return customerRepository.delete(customer);
    }
}
