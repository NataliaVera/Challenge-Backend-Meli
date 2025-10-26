package com.challengemeli.model.exception;

public class NotificationException extends RuntimeException{

    private final String code;
    public NotificationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public NotificationException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
