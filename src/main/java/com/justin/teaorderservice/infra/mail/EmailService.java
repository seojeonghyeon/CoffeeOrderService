package com.justin.teaorderservice.infra.mail;

public interface EmailService {
    void sendEmail(EmailMessage emailMessage);
}
