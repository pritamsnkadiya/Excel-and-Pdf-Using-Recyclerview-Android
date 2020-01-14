package com.example.documetapp.api;

import com.example.documetapp.model.RequestModel;
import com.example.documetapp.model.ResponsModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("/v2/5e1d5274360000521dc73f3e")
    Call<ResponsModel> getAllList();
    //----------------------------------------------------------------------------------------------------------------------------
}
