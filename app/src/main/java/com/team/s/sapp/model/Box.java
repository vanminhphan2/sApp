package com.team.s.sapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Box implements Serializable {

    private String idSender;
    private String idRecipient;
    private String idBox;
    private ArrayList<Message> messages;

    public Box() {
    }

    public Box(String idSender, String idRecipient, String idBox, ArrayList<Message> messages) {
        this.idSender = idSender;
        this.idRecipient = idRecipient;
        this.idBox = idBox;
        this.messages = messages;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdRecipient() {
        return idRecipient;
    }

    public void setIdRecipient(String idRecipient) {
        this.idRecipient = idRecipient;
    }

    public String getIdBox() {
        return idBox;
    }

    public void setIdBox(String idBox) {
        this.idBox = idBox;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
