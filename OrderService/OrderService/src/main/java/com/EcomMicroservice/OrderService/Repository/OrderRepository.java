package com.EcomMicroservice.OrderService.Repository;
import com.EcomMicroservice.OrderService.Collections.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String>  {
}
