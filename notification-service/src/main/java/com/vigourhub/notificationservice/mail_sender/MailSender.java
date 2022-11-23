package com.vigourhub.notificationservice.mail_sender;

public interface MailSender {

    void sendEmail(EmailNotificationType type, String payload) throws Exception;
}
