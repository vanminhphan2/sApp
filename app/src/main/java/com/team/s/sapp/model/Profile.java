package com.team.s.sapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Profile implements Serializable {

    @SerializedName("userId")
    private int id;
    @SerializedName("userName")
    private String userName;
    @SerializedName("yearOfBirth")
    private int yearOfBirth;
    @SerializedName("imageUser")
    private String imgUser;
    @SerializedName("gender")
    private int gender;
    @SerializedName("info")
    private boolean info;
    @SerializedName("phone")
    private String phone;
    @SerializedName("ratioImage")
    private Float ratioImage;

    private boolean isLogin;
    private boolean isOnline;

    public Profile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public boolean isInfo() {
        return info;
    }

    public void setInfo(boolean info) {
        this.info = info;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getRatioImage() {
        return ratioImage;
    }

    public void setRatioImage(Float ratioImage) {
        this.ratioImage = ratioImage;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", imgUser='" + imgUser + '\'' +
                ", gender=" + gender +
                ", info=" + info +
                ", phone='" + phone + '\'' +
                ", ratioImage=" + ratioImage +
                ", isLogin=" + isLogin +
                ", isOnline=" + isOnline +
                '}';
    }
}
