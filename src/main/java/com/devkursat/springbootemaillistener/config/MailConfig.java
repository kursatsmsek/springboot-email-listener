package com.devkursat.springbootemaillistener.config;

import com.devkursat.springbootemaillistener.repository.IncomingMailRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Session;
import java.util.Properties;

//@Configuration
public class MailConfig {

//    @Bean
//    public Session mailSession() {
//        Properties props = new Properties();
//        props.setProperty("mail.store.protocol", "imaps");
//        props.setProperty("mail.imaps.host", "imap.gmail.com");
//        props.setProperty("mail.imaps.port", "993");
//
//        Session session = Session.getInstance(props);
//
//        return session;
//    }

//    @Bean
//    public EmailListener emailListener() {
//        return new EmailListener(mailSession(), "kursatsimsekodevler@gmail.com", "pbuadcyiqztajvrs", incomingMailRepository());
//    }
}
