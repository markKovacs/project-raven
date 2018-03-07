package com.raven.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailService {

    @Value(value = "${spring.mail.username}")
    private String MAIL_FROM;

    @Value(value = "${raven.application.url}")
    private String APPLICATION_URL;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String mailTo, String firstName, String activation, EmailType emailType) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MAIL_FROM);
            message.setTo(mailTo);
            if (emailType.requiresName) {
                String name = firstName == null ? "User" : firstName;
                message.setSubject(String.format(emailType.subject, name));
                if (emailType.requiresActivation) {
                    message.setText(String.format(emailType.text, name, APPLICATION_URL, activation));
                } else {
                    message.setText(String.format(emailType.text, name));
                }
            } else {
                message.setSubject(emailType.subject);
                message.setText(emailType.text);
            }
            javaMailSender.send(message);
            log.info("Email to '" + mailTo + "' was successfully sent.");
        } catch (Exception e) {
            log.error("Email to '" + mailTo + "' could not be sent. Error: " + e);
        }
    }

    public void sendEmail(String mailTo, String firstName, EmailType emailType) {
        sendEmail(mailTo, firstName, null, emailType);
    }

    public void sendEmail(String mailTo, EmailType emailType) {
        sendEmail(mailTo, null, emailType);
    }

    public enum EmailType {
        WELCOME("Welcome %s at Project Raven!", true, false,
                "Dear %s,%n%nWe hope you are going to have a great user experience at Project Raven." +
                        "%n%nBest regards,%nProject Raven Team"),
        ACTIVATION("Activate your account", true, true,
                "Dear %s,%n%nPlease activate your account by clicking the link below." +
                        "%n%n%s/activation/%s" +
                        "%n%nBest regards,%nProject Raven Team");

        private String subject;
        private boolean requiresName;
        private boolean requiresActivation;
        private String text;

        EmailType(String subject, boolean requiresName, boolean requiresActivation, String text) {
            this.subject = subject;
            this.requiresName = requiresName;
            this.requiresActivation = requiresActivation;
            this.text = text;
        }
    }

}
