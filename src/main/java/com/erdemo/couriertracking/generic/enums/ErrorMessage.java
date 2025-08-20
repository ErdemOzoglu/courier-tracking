package com.erdemo.couriertracking.generic.enums;

public enum ErrorMessage implements BaseErrorMessage{

    ITEM_NOT_FOUND("Item not found!"),
    ;

    private String message;
    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
