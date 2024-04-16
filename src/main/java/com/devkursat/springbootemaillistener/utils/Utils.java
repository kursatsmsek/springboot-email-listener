package com.devkursat.springbootemaillistener.utils;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import java.io.IOException;


public class Utils {

    public static String getTextFromEmailMessage(Part message) throws MessagingException, IOException {
        if (message.isMimeType("text/*")) {
            return (String) message.getContent();
        }
        else if (message.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) message.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getTextFromEmailMessage(bp);
                } else if (bp.isMimeType("text/html")) {
                    String s = getTextFromEmailMessage(bp);
                    if (s != null)
                        return s;
                } else {
                    return getTextFromEmailMessage(bp);
                }
            }
            return text;
        } else if (message.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) message.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getTextFromEmailMessage(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }
}
