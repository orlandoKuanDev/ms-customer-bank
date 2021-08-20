package com.bootcamp.mscustomer.models.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotEmpty;

/**
 * The type Customer type.
 */
@Document(collection = "customer_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerType {
    @Id
    @NotEmpty
    private String id;
    private String code;
    private String name;
}
