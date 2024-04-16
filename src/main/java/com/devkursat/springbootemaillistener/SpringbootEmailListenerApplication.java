package com.devkursat.springbootemaillistener;

import com.devkursat.springbootemaillistener.config.EmailListener;
import com.devkursat.springbootemaillistener.config.MailConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SpringbootEmailListenerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEmailListenerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MailConfig.class);
        EmailListener emailListener = context.getBean(EmailListener.class);
        emailListener.startListening();
    }

}
