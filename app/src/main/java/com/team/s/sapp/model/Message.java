package com.team.s.sapp.model;

public class Message {

    private String idMess;
    private String idSender;
    private String messText;
    private String messImage;
    private Float ratioImage;
    private String timeSend;

    public Message() {
    }

    public Message(String idMess, String idSender, String messText, String messImage, Float ratioImage, String timeSend) {
        this.idMess = idMess;
        this.idSender = idSender;
        this.messText = messText;
        this.messImage = messImage;
        this.ratioImage = ratioImage;
        this.timeSend = timeSend;
    }

    public String getIdMess() {
        return idMess;
    }

    public void setIdMess(String idMess) {
        this.idMess = idMess;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getMessText() {
        return messText;
    }

    public void setMessText(String messText) {
        this.messText = messText;
    }

    public String getMessImage() {
        return messImage;
    }

    public void setMessImage(String messImage) {
        this.messImage = messImage;
    }

    public Float getRatioImage() {
        return ratioImage;
    }

    public void setRatioImage(Float ratioImage) {
        this.ratioImage = ratioImage;
    }

    public String getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(String timeSend) {
        this.timeSend = timeSend;
    }
}
