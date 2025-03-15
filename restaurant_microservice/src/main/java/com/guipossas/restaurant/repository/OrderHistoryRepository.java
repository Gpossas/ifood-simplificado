package com.guipossas.restaurant.repository;

import com.guipossas.restaurant.domain.OrderHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends MongoRepository<OrderHistory, String>{}
