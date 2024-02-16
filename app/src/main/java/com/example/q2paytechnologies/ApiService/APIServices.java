package com.example.q2paytechnologies.ApiService;


import com.example.q2paytechnologies.ModelClass.ProductModel;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIServices {

    @GET(ApiUrl.PRODUCT)
    Call<JsonObject>get_product_api();

    @GET("products/{id}")
    Call< ProductModel > getProduct( @Path("id") int id);








}
