package com.vigourhub.backend.publishers;

public enum NotificationQueues {
    AccountCreated("account.created"),
    UserInvited("user.invited");

    private final String value;
    NotificationQueues(String s) {
        this.value = s;
    }

    public String value() {
        return this.value;
    }
}
