package com.eventdriven.common.constant;

public enum MessageType {
    TEXT("TEXT"),
    IMAGE("IMAGE"),
    FILE("FILE"),
    VOICE("VOICE"),
    VIDEO("VIDEO"),
    SYSTEM("SYSTEM"),
    EMOJI("EMOJI");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}