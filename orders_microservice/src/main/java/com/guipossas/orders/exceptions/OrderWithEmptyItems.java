package com.guipossas.orders.exceptions;

public class OrderWithEmptyItems extends RuntimeException {
    public OrderWithEmptyItems() {
        super("O pedido nao pode estar vazio");
    }
}
