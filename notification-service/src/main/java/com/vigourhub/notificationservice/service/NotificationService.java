package com.vigourhub.notificationservice.service;

import com.vigourhub.notificationservice.dto.NotificationQueues;
import com.vigourhub.notificationservice.dto.UserInfoDto;

public interface NotificationService {

    void handle(NotificationQueues queue, UserInfoDto userInfoDto);
}
