package com.eventdriven.common.constant;

public enum UserStatus {
    ONLINE("ONLINE"),
    OFFLINE("OFFLINE"),
    AWAY("AWAY"),
    BUSY("BUSY"),
    INVISIBLE("INVISIBLE");

    private final String status;

    UserStatus(String status) {
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