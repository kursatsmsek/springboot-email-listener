package com.devkursat.springbootemaillistener.config;

import com.devkursat.springbootemaillistener.utils.Utils;
import com.sun.mail.imap.IMAPFolder;

import javax.mail.*;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

public class EmailListener {
    private Session session;
    private String username;
    private String password;

    public EmailListener(Session session, String username, String password) {
        this.session = session;
        this.username = username;
        this.password = password;
    }

    public void startListening() throws MessagingException {
        Store store = session.getStore("imaps");
        store.connect(username, password);

        IMAPFolder inbox = (IMAPFolder)store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        // Create a new thread to keep the connection alive
        Thread keepAliveThread = new Thread(new KeepAliveRunnable(inbox), "IdleConnectionKeepAlive");
        keepAliveThread.start();

        inbox.addMessageCountListener(new MessageCountAdapter() {
            @Override
            public void messagesAdded(MessageCountEvent event) {
                // Process the newly added messages
                Message[] messages = event.getMessages();
                for (Message message : messages) {
                    try {
                        // Implement your email processing logic here
                        System.out.println("New email received");
                        System.out.println("subject => " + message.getSubject());
                        System.out.println("description => " + message.getDescription());
                        System.out.println("content => " + message.getContent());
                        System.out.println("contentType => " + message.getContentType());
                        System.out.println("folder => " + message.getFolder());
                        System.out.println("message number => " + message.getMessageNumber());
                        System.out.println("disposition => " + message.getDisposition());
                        System.out.println("file name => " + message.getFileName());
                        System.out.println("session => " + message.getSession().toString());
                        System.out.println("flags => " + message.getFlags());
                        System.out.println("text => " + Utils.getTextFromEmailMessage(message));
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        });

        // Start the IDLE Loop
        while (!Thread.interrupted()) {
            try {
                inbox.idle();
            } catch (MessagingException e) {
                System.err.println("Messaging exception during IDLE");
                throw new RuntimeException(e);
            }
        }

        // Interrupt and shutdown the keep-alive thread
        if (keepAliveThread.isAlive()) {
            keepAliveThread.interrupt();
        }
    }
}
