package com.kozhevnikov.TechTask.exceptions;

import com.kozhevnikov.TechTask.exceptions.BankException;

public class ResourceNotFoundException extends BankException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}