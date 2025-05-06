package com.universitatea.exception;

public class UnauthorizedRoleException  extends RuntimeException {
    public UnauthorizedRoleException (String message) {
        super(message);
    }
}
