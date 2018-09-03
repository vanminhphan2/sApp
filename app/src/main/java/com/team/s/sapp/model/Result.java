package com.team.s.sapp.model;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("AccountByPhone")
    private Account AccountByPhone;

    @SerializedName("userById")
    private Profile userById;

    public Result(String message, Account accountByPhone) {
        this.message = message;
        AccountByPhone = accountByPhone;
    }

    public Profile getUserById() {
        return userById;
    }

    public Account getAccountByPhone() {
        return AccountByPhone;
    }

    public Boolean getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}