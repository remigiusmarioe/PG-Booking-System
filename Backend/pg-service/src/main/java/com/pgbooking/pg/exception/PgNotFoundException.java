package com.pgbooking.pg.exception;

public class PgNotFoundException extends RuntimeException {
    public PgNotFoundException(String message) {
        super(message);
    }
}
