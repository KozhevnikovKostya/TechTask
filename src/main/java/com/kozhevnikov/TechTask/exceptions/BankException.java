package com.kozhevnikov.TechTask.exceptions;

/**
 * Base class for all task exception
 */
public class BankException extends RuntimeException {
    public BankException(String format) {
        super(format);
    }
}
