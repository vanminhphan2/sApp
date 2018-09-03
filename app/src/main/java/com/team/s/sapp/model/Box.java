package com.team.s.sapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Box implements Serializable {

    @SerializedName("idSender")
    private String idSender;
    @SerializedName("idRecipient")
    private String idRecipient;
    @SerializedName("idBox")
    private String idBox;
    private ArrayList<Message> messages;
    private int toDayIsMonday;

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

    @Override
    public String toString() {
        return "Box{" +
                "idSender='" + idSender + '\'' +
                ", idRecipient='" + idRecipient + '\'' +
                ", idBox='" + idBox + '\'' +
                '}';
    }
}
