package com.team.s.sapp.model.story;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Stories implements Serializable {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("AccountName")
    @Expose
    private String accountName;
    @SerializedName("Topic")
    @Expose
    private String topic;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Audio")
    @Expose
    private String audio;
    @SerializedName("Image")
    @Expose
    private String image;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}


