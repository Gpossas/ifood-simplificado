package com.guipossas.orders.exceptions;

public class QuantityNotPositive extends RuntimeException {
    public QuantityNotPositive() {
        super("Quantidade deve ser maior que zero");
    }
}
