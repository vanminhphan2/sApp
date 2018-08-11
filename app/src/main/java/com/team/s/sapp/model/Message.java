package com.team.s.sapp.model;

public class Message {

    private String idUser;
    private String idMess;
    private String text;
    private String image;
    private Float ratioImage;
    private String timeSend;

    public Message(String idUser, String idMess, String text, String image, Float ratioImage, String timeSend) {
        this.idUser = idUser;
        this.idMess = idMess;
        this.text = text;
        this.image = image;
        this.ratioImage = ratioImage;
        this.timeSend = timeSend;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdMess() {
        return idMess;
    }

    public void setIdMess(String idMess) {
        this.idMess = idMess;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
