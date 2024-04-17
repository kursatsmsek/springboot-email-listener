package com.devkursat.springbootemaillistener.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.mail.Folder;
import java.util.Date;

@Entity
public class IncomingEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private String contentType;

    private String folder;

    private String text;

    private String senderName;

    private String senderEmail;

    private Date receivedDate;

    public IncomingEmail(String subject, String contentType, String folder, String text, String senderName, String senderEmail, Date receivedDate) {
        this.subject = subject;
        this.contentType = contentType;
        this.folder = folder;
        this.text = text;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.receivedDate = receivedDate;
    }

    public IncomingEmail() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "IncomingEmail{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", contentType='" + contentType + '\'' +
                ", folder='" + folder + '\'' +
                ", text='" + text + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", receivedDate=" + receivedDate +
                '}';
    }
}
