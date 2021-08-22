package com.bootcamp.mscustomer.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
//import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * The type Customer.
 */
@Document(collection = "customers")
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Builder
@AllArgsConstructor
public class Customer {
    @Id
    private String id;

    @Field(name = "customerIdentityType")
    private String customerIdentityType;
    @Indexed(unique=true)
    @Field(name = "customerIdentityNumber")
    private String customerIdentityNumber;
    @Size(max = 40)
    @Field(name = "name")
    private String name;
    @Size(max = 75)
    @Email
    @Field(name = "email")
    private String email;
    @Size(max = 9)
    @Field(name = "phone")
    private String phone;
    @Field(name = "address")
    private String address;
    @Field(name = "customerType")
    private CustomerType customerType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();
}
