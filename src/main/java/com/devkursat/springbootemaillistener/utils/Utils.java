package com.devkursat.springbootemaillistener.utils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    public static String getSenderName(Address[] from) {
        String fromString = from[0].toString();
        if (fromString == null) return null;

        String namePattern = "\"(.*?)\"";

        Pattern nameRegex = Pattern.compile(namePattern);
        Matcher nameMatcher = nameRegex.matcher(fromString);

        if (nameMatcher.find()) return nameMatcher.group(1);
        else return null;
    }

    public static String getSenderEmail(Address[] from) {
        String fromString = from[0].toString();
        if (fromString == null) return null;

        String emailPattern = "<(.*?)>";

        Pattern emailRegex = Pattern.compile(emailPattern);
        Matcher emailMatcher = emailRegex.matcher(fromString);

        if (emailMatcher.find()) return emailMatcher.group(1);
        else return null;
    }
}
