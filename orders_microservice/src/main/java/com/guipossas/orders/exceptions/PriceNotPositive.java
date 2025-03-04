package com.guipossas.orders.exceptions;

public class PriceNotPositive extends RuntimeException {
    public PriceNotPositive() {
        super("Preço deve ser maior que zero");
    }
}
