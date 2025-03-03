package com.guipossas.orders.exceptions;

public class OrderItemNotFound extends RuntimeException {
    public OrderItemNotFound(String id) {
        super("Não foi possivel encontrar o item com id:" + id);
    }
}
