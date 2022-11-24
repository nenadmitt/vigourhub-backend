package com.vigourhub.notificationservice.mail_sender;

import com.vigourhub.notificationservice.dto.EmailContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MailgunMailSender implements MailSender{

    JavaMailSender mailSender;
    TemplateEngine templateEngine;

    @Autowired
    public MailgunMailSender(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    private void init() {
        
    }

    @Override
    public void sendEmail(EmailContext context) throws Exception {
        final Context ctx = new Context(new Locale("en"));
        ctx.setVariable("name", "Nenad Mitkovic");
        ctx.setVariable("email", "nenadmitt@gmail.com");

        final String htmlContent = this.templateEngine.process("new-account-template.html", ctx);

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject("Welcome to Vigourhub");
        message.setFrom("welcome@vigourhub.com");
        message.setTo(context.getReceiver());

        message.setText(htmlContent, true);
        System.out.println("Sending email notification " + context.getReceiver());
        try {
            this.mailSender.send(message.getMimeMessage());
        }catch(Exception e) {
            System.out.println("Exception ocurred while sending message " + e.getMessage());
        }
    }
}
