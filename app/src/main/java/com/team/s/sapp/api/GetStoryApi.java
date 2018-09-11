package com.team.s.sapp.api;

import com.team.s.sapp.model.story.Stories;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface GetStoryApi {
    @GET("get-story.php")
    Call<List<Stories>> getStory();
}
