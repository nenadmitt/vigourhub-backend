package com.vigourhub.notificationservice.mail_sender;

import com.vigourhub.notificationservice.dto.EmailContext;

public interface MailSender {

    void sendEmail(EmailContext context) throws Exception;
}
