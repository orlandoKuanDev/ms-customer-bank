package com.bootcamp.mscustomer.models.dto;

import com.bootcamp.mscustomer.models.entities.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private String id;
    private String code;
    private String iban;
    private String name;
    private String surname;
    private String phone;
    private String address;
    private CustomerType customerType;
}
