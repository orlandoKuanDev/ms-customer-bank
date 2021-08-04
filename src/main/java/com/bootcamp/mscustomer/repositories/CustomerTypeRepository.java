package com.bootcamp.mscustomer.repositories;

import com.bootcamp.mscustomer.models.entities.CustomerType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerTypeRepository extends ReactiveCrudRepository<CustomerType, Long> {
}
