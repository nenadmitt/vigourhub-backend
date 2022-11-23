package com.vigourhub.notificationservice.mail_sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MailgunMailSender implements MailSender{

    @Value("${mailgun.username}")
    private String username;
    @Value("${mailgun.api-key}")
    private String apiKey;
    @Value("${mailgun.url}")

    private RestTemplate template;

    private void init() {
        
    }

    @Override
    public void sendEmail(EmailNotificationType type, String payload) throws Exception {

    }
}
