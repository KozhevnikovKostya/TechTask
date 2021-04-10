package com.kozhevnikov.TechTask.exceptions;

public class BankException extends RuntimeException {
    public BankException(String format) {
        super(format);
    }
}
