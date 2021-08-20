package com.bootcamp.mscustomer.models.dto;

import com.bootcamp.mscustomer.models.entities.CustomerType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The type Customer dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CustomerDTO {

    private String id;
    private String name;
    private String customerIdentityType;
    private String customerIdentityNumber;
}
