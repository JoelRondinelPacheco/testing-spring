package org.joel.test.springboot.app.sprigboot_test.exceptions;

public class InsufficientMoneyException extends RuntimeException {
    public InsufficientMoneyException(String message) {
        super(message);
    }
}
