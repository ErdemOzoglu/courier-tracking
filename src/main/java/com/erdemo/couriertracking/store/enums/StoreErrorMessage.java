package com.erdemo.couriertracking.store.enums;

import com.erdemo.couriertracking.generic.enums.BaseErrorMessage;

public enum StoreErrorMessage implements BaseErrorMessage {

    COULD_NOT_READ_DATA("Could not read data!");

    private String message;

    StoreErrorMessage(String message) {
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
