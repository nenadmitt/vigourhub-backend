package com.vigourhub.notificationservice.dto;

public enum NotificationQueues {
    AccountCreated("account.created"),
    UserInvited("user.invited");

    final private String value;
    NotificationQueues(String s) {
        this.value = s;
    }

    public String value() {
        return this.value;
    }
}
