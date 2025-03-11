package com.guipossas.orders.repository;

import com.guipossas.orders.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Deprecated(since = "Use DynamoDbTemplate instead")
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {}
