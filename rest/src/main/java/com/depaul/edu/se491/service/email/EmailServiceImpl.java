package com.depaul.edu.se491.service.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Alex on 2/10/2015.
 */
public class EmailServiceImpl implements EmailService {

    private Properties props;
    private String applicationEmail;
    private String applicationPassword;

    public EmailServiceImpl(String applicationEmail, String applicationPassword) {
        this.applicationEmail = applicationEmail;
        this.applicationPassword = applicationPassword;
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    @Override
    public void sendEmail(String recipient, String message) {
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(applicationEmail, applicationPassword);
                    }
                });

        try {

            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(applicationEmail));
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            mimeMessage.setSubject("Alert!");
            mimeMessage.setText(message);

            Transport.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
