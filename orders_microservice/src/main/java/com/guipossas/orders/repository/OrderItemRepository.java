package com.guipossas.orders.repository;

import com.guipossas.orders.domain.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends MongoRepository<OrderItem, String> {

    @Query("{ 'id': { $in: ?0 } }")
    List<OrderItem> findAllIds(List<String> ids);
}
