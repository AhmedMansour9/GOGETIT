package com.gogit.Retrofit

import com.gogit.Model.*
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

    @POST("fillter_search_all_products")
    fun FilterProducts(
        @QueryMap map:Map<String,String>): Call<AllProducts_Response>


    @POST("similar_products")
    fun SimiliarProducts(
        @QueryMap map:Map<String,String>): Call<AllProducts_Response>


    @POST("categories")
    fun Categories(
        @QueryMap map:Map<String,String>): Call<Categories_Response>

    @POST("products_category")
    fun ProductsByCatId(
        @QueryMap map:Map<String,String>): Call<AllProducts_Response>


    @POST("brands")
    fun Brands(
        @QueryMap map:Map<String,String>): Call<Brand_Response>


    @POST("products_images")
    fun SliderProducts(
        @QueryMap map:Map<String,String>): Call<Slider_DetailsProduct>

    @POST("products_size")
    fun SizesProducts(
        @QueryMap map:Map<String,String>): Call<Sizes_Response>

    @POST("filters_all_sizes")
    fun AllSizes(
        @QueryMap map:Map<String,String>): Call<AllSizes_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("update_profile")
    fun EditProf(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Edit_ProfileResponse>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("change_password")
    fun ChangePassword(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Edit_ProfileResponse>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("create_order")
    fun Order(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Order_Response>



    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("add_cart")
    fun AddToCart(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<AddToCart_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("list_data_cart")
    fun getCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Cart_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("plus_quentity_Cart")
    fun PlusCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PlusCart_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("delete_cart")
    fun DeleteCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PlusCart_Response>



    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("min_quentity_Cart")
    fun MinusCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PlusCart_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("user_profile")
    fun Profile(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Profile_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("order_list")
    fun Myorders(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<MyOrders_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("order_list_details")
    fun MyordersDetails(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<OrderDetails_Response>

//    @Field("Email") email: String,
//    @Field("Password") password: String
}