package com.vigourhub.notificationservice.service;

import com.vigourhub.notificationservice.dto.EmailContext;
import com.vigourhub.notificationservice.dto.NotificationQueues;
import com.vigourhub.notificationservice.dto.events.AccountCreatedEvent;
import com.vigourhub.notificationservice.dto.events.UserInvitedEvent;
import com.vigourhub.notificationservice.mail_sender.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService{


    private final String ACCOUNT_CREATED_TEMPLATE = "new-account.html";
    private final String USER_INVITED_TEMPLATE = "user-invited.html";
    private final String MAIL_SENDER="admin@vigourhub.com";
    private final MailSender mailSender;

    @Autowired
    public NotificationServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void handleUserInvited(UserInvitedEvent event) {

        EmailContext context = new EmailContext();
        context.setReceiver(event.getEmail());
        context.setTemplateUrl(USER_INVITED_TEMPLATE);
        context.setSender(MAIL_SENDER);
        context.setTitle("You have been invited to join Vigourhub!");

        Map<String,String> templateContext = new HashMap<>();
        templateContext.put("name", event.getName());
        templateContext.put("invitation-url", String.format("http://localhost:3000/api/v1/register?token=%s", event.getInvitationCode()));

        context.setTemplateContext(templateContext);

        try {
            this.mailSender.sendEmail(context);
        }catch(Exception e) {
            System.out.println("Error ocurred while sending email notification " + e.getMessage());
        }

    }

    public  void handleAccountCreated(AccountCreatedEvent event) {
        EmailContext context = new EmailContext();
        context.setReceiver(event.getOwnerEmail());
        context.setTitle("Your account has been registered successfully");
        context.setTemplateUrl(ACCOUNT_CREATED_TEMPLATE);
        context.setSender(MAIL_SENDER);

        Map<String,String> templateContext = new HashMap<>();
        templateContext.put("name", event.getOwnerName());
        templateContext.put("activation-url", String.format("http://localhost:3000/api/v1/register?token=%s", event.getActivationCode()));

        try {
            this.mailSender.sendEmail(context);
        }catch(Exception e) {
            System.out.println("Error ocurred while sending email notification " + e.getMessage());
        }
    }
}
