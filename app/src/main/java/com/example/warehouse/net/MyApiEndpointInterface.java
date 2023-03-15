package com.example.warehouse.net;

import com.example.warehouse.model.APIResponse;
import com.example.warehouse.model.Material;
import com.example.warehouse.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface MyApiEndpointInterface {

    @FormUrlEncoded
    @POST("users/api/v1/signup")
    Call<APIResponse> userRegister(@Body User user);

    @FormUrlEncoded
    @POST("users/api/v1/login")
    Call<APIResponse> userLogin(@Field("email") String email, @Field("password") String password);

    @GET("materials/api/v1/all")
    Call<APIResponse> getMaterial();

    @GET("materials/api/v1/deletedata")
    Call<APIResponse> getDeleteMaterial();

    @POST("materials/api/v1/add")
    Call<APIResponse> addMaterial(@Body Material material);

    @PUT("materials/api/v1/update")
    Call<APIResponse> updateMaterial(@Query("id") int id, @Body Material material);

    @DELETE("materials/api/v1/delete")
    Call<APIResponse> deleteMaterial(@Query("name") String name);
}
