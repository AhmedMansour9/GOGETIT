package com.gogit.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gogit.Model.Edit_ProfileResponse
import com.gogit.Model.Order_Response
import com.gogit.Retrofit.ApiClient
import com.gogit.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Order_ViewModel : ViewModel() {


    public var listProductsMutableLiveData: MutableLiveData<Order_Response>? = null
    private lateinit var context: Context
    private var Wron_Email:Boolean=false

    fun getStatus():Boolean{
        return Wron_Email
    }

    public fun getData(
        auth:String,order_total_price: String,customer_address:String,customer_phone:String,langtude:String
        ,lattude:String,payment_method:String,payment_status:String,customer_city:String,
        customer_country:String,customer_street:String,customer_postal_code:String,Region:String
        ,B_number:String,F_number:String,Apartment:String,
        context: Context
    ): LiveData<Order_Response> {
        listProductsMutableLiveData = MutableLiveData<Order_Response>()
        this.context = context
        getDataValues(auth,order_total_price,customer_address,customer_phone,langtude,lattude,
            payment_method,payment_status,customer_city,
            customer_country,customer_street,customer_postal_code,Region,B_number,F_number,Apartment)
        return listProductsMutableLiveData as MutableLiveData<Order_Response>
    }

    private fun getDataValues(auth:String,order_total_price: String,customer_address:String,customer_phone:String,langtude:String
                              ,lattude:String,payment_method:String,payment_status:String,customer_city:String,
                              customer_country:String,customer_street:String,customer_postal_code:String,
                              Region:String
                              ,B_number:String,F_number:String,Apartment:String) {
        var hashMap = HashMap<String, String>()
        hashMap.put("order_total_price", order_total_price)
        hashMap.put("customer_address", customer_address)
        hashMap.put("customer_phone", customer_phone)
        hashMap.put("langtude", langtude)
        hashMap.put("lattude", lattude)
        hashMap.put("payment_method", payment_method)
        hashMap.put("payment_status", payment_status)
        hashMap.put("customer_city", customer_city)
        hashMap.put("customer_country", customer_country)
        hashMap.put("customer_street", customer_street)
        hashMap.put("customer_postal_code", customer_postal_code)
        hashMap.put("customer_region", Region)
        hashMap.put("customer_home_number", B_number)
        hashMap.put("customer_floor_number", F_number)
        hashMap.put("customer_appartment_number", Apartment)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.Order(hashMap, "Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<Order_Response> {
            override fun onResponse(
                call: Call<Order_Response>,
                response: Response<Order_Response>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Order_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }





}