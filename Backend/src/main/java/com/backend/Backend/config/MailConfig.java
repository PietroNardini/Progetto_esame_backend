package com.backend.Backend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class MailConfig {

    @Value("${spring.mail.username}") // ottengo i valori dal file application.properties
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");//specifico il server SMTP che voglio utilizzare  (in questo caso Gmail)
        mailSender.setPort(587);//e la rispettiva porta

        mailSender.setUsername(emailUsername);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        //specifico le proprietà del server SMTP che voglio utilizzare
        props.put("mail.transport.protocol", "smtp");//specifico il protocollo di trasporto da utilizzare (SMTP)
        props.put("mail.smtp.auth", "true");//abilito l'autenticazione per il server SMTP
        props.put("mail.smtp.starttls.enable", "true");//abilito la crittografia TLS per avere una connessione sicura
        props.put("mail.debug", "true");//abilito il debug per vedere i messaggi di errore in caso di problemi
        return mailSender;
    }
}