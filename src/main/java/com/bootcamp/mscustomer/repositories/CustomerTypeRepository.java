package com.bootcamp.mscustomer.repositories;

import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.models.entities.CustomerType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerTypeRepository extends ReactiveMongoRepository<CustomerType, String> {
    Mono<CustomerType> findByCode(String code);
}
