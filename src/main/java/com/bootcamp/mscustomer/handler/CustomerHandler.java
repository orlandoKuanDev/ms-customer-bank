package com.bootcamp.mscustomer.handler;

import com.bootcamp.mscustomer.Exception.EntityNotFoundException;
import com.bootcamp.mscustomer.models.entities.Customer;
import com.bootcamp.mscustomer.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class CustomerHandler {
    private final ICustomerService customerService;

    @Autowired
    public CustomerHandler(ICustomerService customerService) {
        this.customerService = customerService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(customerService.findAll(), Customer.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable("id");
        return errorHandler(
                customerService.findById(id).flatMap(customer -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(customer))
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> findByCustomerIdentityNumber(ServerRequest request){
        String customerIdentityNumber = request.pathVariable("customerIdentityNumber");
        return errorHandler(
                customerService.findByCustomerIdentityNumber(customerIdentityNumber).flatMap(customer -> ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(customer))
                        .switchIfEmpty(Mono.error(new EntityNotFoundException(
                                String.format("THE PRODUCT NAME DONT EXIST IN MICRO SERVICE PRODUCT-> %s", customerIdentityNumber)
                        )))
        );
    }

    public Mono<ServerResponse> save(ServerRequest request){
        Mono<Customer> bill = request.bodyToMono(Customer.class);
        return bill.flatMap(customerService::create)
                .flatMap(customer -> ServerResponse.created(URI.create("/customer/".concat(customer.getId())))
                        .contentType(APPLICATION_JSON)
                        .bodyValue(customer))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;
                    if(errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return ServerResponse.badRequest()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(errorResponse.getResponseBodyAsString());
                    }
                    return Mono.error(errorResponse);
                });
    }

    public Mono<ServerResponse> update(ServerRequest request){
        Mono<Customer> bill = request.bodyToMono(Customer.class);
        return bill.flatMap(customerEdit ->
                        customerService.findByCustomerIdentityNumber(customerEdit.getCustomerIdentityNumber())
                        .flatMap(currentCustomer -> {
                            currentCustomer.setCustomerIdentityType(customerEdit.getCustomerIdentityType());
                            currentCustomer.setCustomerIdentityNumber(customerEdit.getCustomerIdentityNumber());
                            currentCustomer.setName(customerEdit.getName());
                            currentCustomer.setAddress(customerEdit.getAddress());
                            currentCustomer.setPhone(customerEdit.getPhone());
                            currentCustomer.setEmail(customerEdit.getEmail());
                            return customerService.update(currentCustomer);
                        })).flatMap(billUpdate -> ServerResponse.created(URI.create("/customer/".concat(billUpdate.getId())))
                        .contentType(APPLICATION_JSON)
                        .bodyValue(billUpdate))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error update customer")));
    }

    private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response){
        return response.onErrorResume(error -> {
            WebClientResponseException errorResponse = (WebClientResponseException) error;
            if(errorResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                Map<String, Object> body = new HashMap<>();
                body.put("error", "No existe el producto: ".concat(errorResponse.getMessage()));
                body.put("timestamp", new Date());
                body.put("status", errorResponse.getStatusCode().value());
                return ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(body);
            }
            return Mono.error(errorResponse);
        });
    }
}
