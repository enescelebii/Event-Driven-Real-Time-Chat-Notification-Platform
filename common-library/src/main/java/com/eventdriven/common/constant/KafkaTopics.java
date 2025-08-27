package com.eventdriven.common.constant;

public class KafkaTopics {

    public static final String MESSAGE_SENT = "message-sent";
    public static final String MESSAGE_READ = "message-read";
    public static final String MESSAGE_DELIVERED = "message-delivered";

    public static final String USER_ONLINE = "user-online";
    public static final String USER_OFFLINE = "user-offline";
    public static final String USER_TYPING = "user-typing";

    public static final String NOTIFICATION_SEND = "notification-send";
    public static final String NOTIFICATION_READ = "notification-read";

    public static final String FRIEND_REQUEST_SENT = "friend-request-sent";
    public static final String FRIEND_REQUEST_ACCEPTED = "friend-request-accepted";
    public static final String FRIEND_REQUEST_REJECTED = "friend-request-rejected";

    public static final String SYSTEM_NOTIFICATION = "system-notification";
    public static final String USER_ACTIVITY = "user-activity";

    private KafkaTopics() {
        // Prevent instantiation
    }
}