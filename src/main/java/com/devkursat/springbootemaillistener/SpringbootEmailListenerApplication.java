package com.devkursat.springbootemaillistener;

import com.devkursat.springbootemaillistener.config.EmailListener;
import com.devkursat.springbootemaillistener.config.MailConfig;
import com.devkursat.springbootemaillistener.repository.IncomingMailRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.mail.Session;
import java.util.Properties;

@SpringBootApplication
public class SpringbootEmailListenerApplication implements CommandLineRunner {

    public SpringbootEmailListenerApplication(IncomingMailRepository incomingMailRepository) {
        this.incomingMailRepository = incomingMailRepository;
    }

    @Bean
    public Session mailSession() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", "imap.gmail.com");
        props.setProperty("mail.imaps.port", "993");

        Session session = Session.getInstance(props);

        return session;
    }

    private final IncomingMailRepository incomingMailRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEmailListenerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MailConfig.class);
        EmailListener emailListener = new EmailListener(mailSession(), "kursatsimsekodevler@gmail.com", "pbuadcyiqztajvrs", incomingMailRepository);
        emailListener.startListening();

//        EmailListener emailListener = context.getBean(EmailListener.class);
//        emailListener.startListening();
    }

}
