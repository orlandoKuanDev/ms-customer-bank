package com.bootcamp.mscustomer.data;

import com.bootcamp.mscustomer.models.entities.Customer;

public class DataProvider {
    public static Customer CustomerRequest() {
        return Customer.builder()
                .id("1")
                .customerIdentityType("DNI")
                .customerIdentityNumber("70055041")
                .customerType("PERSONAL")
                .name("Fabio")
                .email("fabio@tecsup.edu.pe")
                .phone("945865844")
                .address("Urb.Mariscal Caceres")
                .build();
    }
    public static Customer CustomerRequest2() {
        return Customer.builder()
                .id("1")
                .customerIdentityType("RUC")
                .customerIdentityNumber("2011111111")
                .customerType("ENTERPRISE")
                .name("EVERIS PERU SAC")
                .email("gerencia@everis.pe")
                .phone("945865844")
                .address("LIMA")
                .build();
    }
}
