package com.example.pizzastore.service.exception;

import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends BaseException {
    public ItemNotFoundException() {
        super("Item not found!", HttpStatus.NO_CONTENT.value());
    }
}
