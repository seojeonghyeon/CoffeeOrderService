package me.justin.coffeeorderservice.infra.mail;

public interface EmailService {
    void sendEmail(EmailMessage emailMessage);
}
