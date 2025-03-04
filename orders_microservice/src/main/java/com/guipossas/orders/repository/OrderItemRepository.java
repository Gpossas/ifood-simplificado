package com.guipossas.orders.repository;

import com.guipossas.orders.domain.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends MongoRepository<OrderItem, String> {
}
