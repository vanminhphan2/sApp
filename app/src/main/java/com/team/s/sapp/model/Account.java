package com.team.s.sapp.model;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("accountId")
    private int accountId;
    @SerializedName("phone")
    private int phone;
    @SerializedName("password")
    private String password;


    public Account(int accountId, int phone, String password) {
        this.accountId = accountId;
        this.phone = phone;
        this.password = password;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                '}';
    }
}
