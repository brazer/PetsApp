package com.example.myapplication.net;

import com.example.myapplication.net.model.PetsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api.php")
    Call<PetsResponse> getPets(@Query("query") String value);
}
