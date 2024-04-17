package com.devkursat.springbootemaillistener.config;

import com.devkursat.springbootemaillistener.model.IncomingEmail;
import com.devkursat.springbootemaillistener.repository.IncomingMailRepository;
import com.devkursat.springbootemaillistener.utils.Utils;
import com.sun.mail.imap.IMAPFolder;

import javax.mail.*;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

public class EmailListener {
    private final Session session;
    private final String username;
    private final String password;

    private final IncomingMailRepository incomingMailRepository;

    public EmailListener(Session session, String username, String password, IncomingMailRepository incomingMailRepository) {
        this.session = session;
        this.username = username;
        this.password = password;
        this.incomingMailRepository = incomingMailRepository;
    }

    public void startListening() throws MessagingException {
        Store store = session.getStore("imaps");
        store.connect(username, password);

        IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
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

                        IncomingEmail incomingEmail = new IncomingEmail(
                                message.getSubject(),
                                message.getContentType(),
                                message.getFolder().toString(),
                                Utils.getTextFromEmailMessage(message),
                                Utils.getSenderName(message.getFrom()),
                                Utils.getSenderEmail(message.getFrom()),
                                message.getReceivedDate()
                        );
                        incomingMailRepository.save(incomingEmail);
                        System.out.println(incomingEmail);
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
