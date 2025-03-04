package com.guipossas.orders.exceptions;

public class OrderNotFound extends RuntimeException {
    public OrderNotFound(String id) {
        super("Nao foi possivel encontrar o pedido com id:" + id);
    }
}
