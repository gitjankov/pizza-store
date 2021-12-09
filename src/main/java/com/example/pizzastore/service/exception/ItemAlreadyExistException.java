package com.example.pizzastore.service.exception;

import org.springframework.http.HttpStatus;

public class ItemAlreadyExistException extends BaseException {
    private static final long serialVersionUID = 1L;

    public ItemAlreadyExistException() {
        super("Item already exists!",HttpStatus.FORBIDDEN.value());
    }
}
