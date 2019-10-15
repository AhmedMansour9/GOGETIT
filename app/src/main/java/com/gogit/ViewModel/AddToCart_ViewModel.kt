package com.gogit.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gogit.Model.AddToCart_Response
import com.gogit.Model.Edit_ProfileResponse
import com.gogit.Retrofit.ApiClient
import com.gogit.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddToCart_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<AddToCart_Response>? = null
    private lateinit var context: Context
    private var Wron_Email:Boolean=false

    fun getStatus():Boolean{
        return Wron_Email
    }

    public fun getData(
        auth:String,
        product_id: String,
        product_quantity:String,
        color_id:String,
        size_id:String,
        context: Context
    ): LiveData<AddToCart_Response> {
        listProductsMutableLiveData = MutableLiveData<AddToCart_Response>()
        this.context = context
        getDataValues(auth,product_id,product_quantity,color_id,size_id)
        return listProductsMutableLiveData as MutableLiveData<AddToCart_Response>
    }


    private fun getDataValues(auth:String,product_id: String,product_quantity:String,color_id:String,size_id:String) {
        var map = HashMap<String, String>()
        if (product_id != null) {
            map.put("product_id", product_id)
        }
        if (product_quantity != null) {
            map.put("product_quantity", product_quantity)
        }

            map.put("color_id","58")
        map.put("size", size_id)

         var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.AddToCart(map, "Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<AddToCart_Response> {
            override fun onResponse(
                call: Call<AddToCart_Response>,
                response: Response<AddToCart_Response>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AddToCart_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }




}