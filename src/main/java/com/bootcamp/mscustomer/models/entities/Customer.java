package com.bootcamp.mscustomer.models.entities;

import lombok.*;
//import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection = "customers")
@Getter
@Setter
@AllArgsConstructor
public class Customer {
    @Id
    private String id;
    @NotBlank
    private String code;
    private String iban;
    @Size(max = 40)
    private String name;
    private String surname;
    @Size(max = 75)
    @Email
    private String email;
    @Size(max = 9)
    private String phone;
    private String address;
    @Valid
    private CustomerType customerType;
}
