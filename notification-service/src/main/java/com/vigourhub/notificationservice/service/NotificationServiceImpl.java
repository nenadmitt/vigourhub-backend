package com.vigourhub.notificationservice.service;

import com.vigourhub.notificationservice.dto.EmailContext;
import com.vigourhub.notificationservice.dto.NotificationQueues;
import com.vigourhub.notificationservice.dto.UserInfoDto;
import com.vigourhub.notificationservice.mail_sender.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{

    private final MailSender mailSender;

    @Autowired
    public NotificationServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void handle(NotificationQueues queue, UserInfoDto userInfoDto) {
        if (queue == NotificationQueues.AccountCreated) {
            this.accountCreatedNotification(userInfoDto);
        }else if( queue.equals(NotificationQueues.UserInvited)){
            this.userInvitedNotification(userInfoDto);
        }
    }

    private void userInvitedNotification(UserInfoDto userInfoDto) {

        EmailContext context = new EmailContext();
        context.setReceiver("nenadmitt@gmail.com");
        try {
            this.mailSender.sendEmail(context);
        }catch(Exception e) {
            System.out.println("Error ocurred while sending email notification " + e.getMessage());
        }

    }

    private void accountCreatedNotification(UserInfoDto userInfoDto) {
        EmailContext context = new EmailContext();
        context.setReceiver("nenadmitt@gmail.com");
        try {
            this.mailSender.sendEmail(context);
        }catch(Exception e) {
            System.out.println("Error ocurred while sending email notification " + e.getMessage());
        }
    }
}
