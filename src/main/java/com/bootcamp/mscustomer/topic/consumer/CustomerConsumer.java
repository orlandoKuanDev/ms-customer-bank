package com.bootcamp.mscustomer.topic.consumer;

import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.services.ICustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomerConsumer {
    private final static String SERVICE_WALLET_TOPIC = "service-wallet-topic";
    private final static String GROUP_ID = "customer-group";

    private final ICustomerService customerService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerConsumer(ICustomerService customerService, ObjectMapper objectMapper) {
        this.customerService = customerService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener( topics = SERVICE_WALLET_TOPIC, groupId = GROUP_ID)
    public Disposable retrieveSavedCustomer(String data) throws Exception {
        log.info("data from kafka listener (customer) =>"+data);
        Customer customer= objectMapper.readValue(data, Customer.class );

        return Mono.just(customer)
                .log()
                .flatMap(customerService::create)
                .subscribe();
    }
}
