package com.example.demo.model.common;

public class ReasonTypeNotFound extends RuntimeException {

    public ReasonTypeNotFound(String reasonTypeName) {
        super(String.format("Reason type with name %s not found", reasonTypeName));
    }
}