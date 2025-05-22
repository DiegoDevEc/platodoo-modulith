package com.playtodoo.modulith.users.exception;

public class TechnicalErrorException extends RuntimeException {
    public TechnicalErrorException(String message) {
        super(message);
    }
}