package com.EcomMicroservice.OrderService.Service;
import com.EcomMicroservice.OrderService.Collections.Order;
import com.EcomMicroservice.OrderService.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderRepository OR;
    @Autowired
    public OrderService(OrderRepository orderrepository) {
        this.OR = orderrepository;
    }
}
