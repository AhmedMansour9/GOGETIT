package com.gogit.Retrofit

import com.gogit.Model.AllProducts_Response
import com.gogit.Model.Register_Model
import com.gogit.Model.SliderHome_Model
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @POST("register")
     fun userRegister(
        @QueryMap map:Map<String,String>): Call<Register_Model>

    @POST("login")
    fun userLogin(
        @QueryMap map:Map<String,String>): Call<Register_Model>

    @POST("slider")
    fun SLider(
        @QueryMap map:Map<String,String>): Call<SliderHome_Model>

    @POST("all_products")
    fun AllProducts(
        @QueryMap map:Map<String,String>): Call<AllProducts_Response>

    @POST("latest_products")
    fun LatestProducts(
        @QueryMap map:Map<String,String>): Call<AllProducts_Response>

    @POST("latest_products")
    fun Categories(
        @QueryMap map:Map<String,String>): Call<Categories_Response>



//    @Field("Email") email: String,
//    @Field("Password") password: String
}