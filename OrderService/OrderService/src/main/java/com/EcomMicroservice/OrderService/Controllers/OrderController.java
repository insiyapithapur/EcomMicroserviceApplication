package com.EcomMicroservice.OrderService.Controllers;
import com.EcomMicroservice.OrderService.Collections.Order;
import com.EcomMicroservice.OrderService.Repository.OrderRepository;
import com.EcomMicroservice.OrderService.Service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("OS")
public class OrderController {

    private WebClient.Builder WCB;
    private OrderService OS;
    private OrderRepository OR;

    @Autowired
    public OrderController(OrderService orderservice , WebClient.Builder webClientBuilder , OrderRepository orderrepository) {
        this.OS = orderservice;
        this.WCB = webClientBuilder;
        this.OR = orderrepository;
    }

//    public OrderController(WebClient.Builder webClientBuilder) {
//        this.WCB = webClientBuilder;
//    }

    @PostMapping("CreateOrder/{Product_ID}")
    @CircuitBreaker(name = "Product" , fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "Product")
//    Make return type as Completable calls
    public ResponseEntity<String> PlacedOrder(@PathVariable String Product_ID){
        List<Order> orders = WCB.build().get()
                .uri("http://ProductService/PS/productId/" + Product_ID)
                .retrieve()
                .bodyToFlux(Order.class)
                .collectList()
                .block();
        if (orders != null) {
            WCB.build().delete()
                    .uri("http://ProductService/PS/" +Product_ID)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            Order order = orders.get(0);
            OR.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully.");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Order cannot create");
        }
    }

    public ResponseEntity<String> fallbackMethod(String Product_ID , RuntimeException runtimeException) {
//        log.info("Cannot Place Order Executing Fallback logic");
        return  ResponseEntity.status(HttpStatus.CONFLICT).body("Oops! Something went wrong, please order after some time!");
    }
}
