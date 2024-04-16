package com.devkursat.springbootemaillistener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;
import javax.mail.Session;
import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String emailHost;

    @Value("${spring.mail.port}")
    private String emailPort;

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Bean
    public Session mailSession() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", "imap.gmail.com");
        props.setProperty("mail.imaps.port", emailPort);

        Session session = Session.getInstance(props);
        session.setDebug(true);

        return session;
    }

    @Bean
    public EmailListener emailListener() throws MessagingException {
        return new EmailListener(mailSession(), "kursatsimsekodevler@gmail.com", "pbuadcyiqztajvrs");
    }
}
