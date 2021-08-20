package com.bootcamp.mscustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * The type Ms customer application.
 */
@EnableEurekaClient
@SpringBootApplication
public class MsCustomerApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MsCustomerApplication.class, args);
    }

}
