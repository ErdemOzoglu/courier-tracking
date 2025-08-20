package com.erdemo.couriertracking.delivery.enums;

public enum EnumDeliveryStatus {

    ACTIVE("Active"),
    COMPLETED("Completed");

    private String status;

    EnumDeliveryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
