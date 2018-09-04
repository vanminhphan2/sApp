package com.team.s.sapp.api;

import com.team.s.sapp.model.Account;
import com.team.s.sapp.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterApi {

    @FormUrlEncoded
    @POST("searchAccountByPhone.php")
    Call<Result>  checkPhone(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("login.php")
    Call<Result>  login(@Field("phone") String phone,@Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<Result>   register(@Field("phone") String phone,
                            @Field("password") String password,
                            @Field("userName") String userName,
                            @Field("yearOfBirth") String yearOfBirth,
                            @Field("imageUser") String imageUser,
                            @Field("gender") String gender,
                            @Field("ratioImage") String ratioImage,
                            @Field("info") String info);
}
