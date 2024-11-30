package com.example.healthconnect;

import android.util.Log;
import android.widget.Toast;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailAPI {

    private final String senderEmail = "andriodproject.douglas@gmail.com"; // Email
    private final String senderPassword = "hadq nqvd prpv adqy"; // Password

    public void sendEmail(String recipientEmail, String subject, String messageBody) {

        Properties props = new Properties();
        String smtpHost = "smtp.gmail.com";
        props.put("mail.smtp.host", smtpHost);
        String smtpPort = "587";
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        new Thread(() -> {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(
                        Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(messageBody);

                Transport.send(message);

                Log.d("JavaMailAPI", "Email sent successfully to " + recipientEmail);

            } catch (MessagingException e) {
                Log.e("JavaMailAPI", "Error while sending email", e);
            }
        }).start();
    }

}
