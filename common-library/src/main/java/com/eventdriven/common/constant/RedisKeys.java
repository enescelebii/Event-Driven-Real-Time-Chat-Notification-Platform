package com.eventdriven.common.constant;

public class RedisKeys {

    public static final String MESSAGE_CACHE_PREFIX = "messages:conversation:";
    public static final String RECENT_MESSAGES_PREFIX = "recent:messages:user:";
    public static final String UNREAD_COUNT_PREFIX = "unread:count:user:";

    public static final String ONLINE_USERS = "online:users";
    public static final String USER_STATUS_PREFIX = "status:user:";
    public static final String USER_LAST_SEEN_PREFIX = "lastseen:user:";

    public static final String TYPING_PREFIX = "typing:conversation:";

    public static final String USER_SESSIONS_PREFIX = "sessions:user:";
    public static final String SESSION_USER_PREFIX = "session:";

    public static final String RATE_LIMIT_PREFIX = "ratelimit:";

    public static final String NOTIFICATION_CACHE_PREFIX = "notifications:user:";

    private RedisKeys() {
    }

    public static String getConversationKey(Long userId1, Long userId2) {
        Long smaller = Math.min(userId1, userId2);
        Long bigger = Math.max(userId1, userId2);
        return MESSAGE_CACHE_PREFIX + smaller + ":" + bigger;
    }

    public static String getUserStatusKey(Long userId) {
        return USER_STATUS_PREFIX + userId;
    }

    public static String getTypingKey(Long userId1, Long userId2) {
        Long smaller = Math.min(userId1, userId2);
        Long bigger = Math.max(userId1, userId2);
        return TYPING_PREFIX + smaller + ":" + bigger;
    }

    public static String getUnreadCountKey(Long userId) {
        return UNREAD_COUNT_PREFIX + userId;
    }
}