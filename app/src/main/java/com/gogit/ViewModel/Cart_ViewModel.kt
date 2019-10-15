package com.gogit.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gogit.Model.Cart_Response
import com.gogit.Model.Edit_ProfileResponse
import com.gogit.Model.PlusCart_Response
import com.gogit.Retrofit.ApiClient
import com.gogit.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Cart_ViewModel : ViewModel()
{
     var listProductsMutableLiveData: MutableLiveData<Cart_Response>? = null
    var PlusProductsMutableLiveData: MutableLiveData<PlusCart_Response>? = null
    var MinusProductsMutableLiveData: MutableLiveData<PlusCart_Response>? = null
    private lateinit var context: Context
     lateinit var call:Any
     fun getData(
        auth:String,
        lang:String,
        context: Context
    ): LiveData<Cart_Response> {
        listProductsMutableLiveData = MutableLiveData<Cart_Response>()
        this.context = context
        getDataValues(auth,lang)
        return listProductsMutableLiveData as MutableLiveData<Cart_Response>
    }
    public fun DeleteData(
        auth:String,
        lang:String,
        cart_id:String,
        context: Context
    ): LiveData<PlusCart_Response> {
        PlusProductsMutableLiveData = MutableLiveData<PlusCart_Response>()
        this.context = context
        getDeleteCart(    auth,lang,cart_id)
        return PlusProductsMutableLiveData as MutableLiveData<PlusCart_Response>
    }
     fun AddPlusData(
        auth:String,
        lang:String,
        cart_id:String,
        Type:String,
        context: Context
    ): LiveData<PlusCart_Response> {
        PlusProductsMutableLiveData = MutableLiveData<PlusCart_Response>()
        this.context = context
        getPlusCart(    auth,lang,cart_id,Type)
        return PlusProductsMutableLiveData as MutableLiveData<PlusCart_Response>
    }
    private fun getDataValues(auth:String,lang:String ) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getCart( map,"Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<Cart_Response> {
            override fun onResponse(
                call: Call<Cart_Response>,
                response: Response<Cart_Response>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Cart_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getPlusCart(auth:String,lang:String,cart_id:String,Type:String ) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        map.put("cart_id",cart_id)
        var service = ApiClient.getClient()?.create(Service::class.java)
        if(Type.equals("min")){
             call = service?.PlusCart( map,"Bearer " + auth)!!
        }else {
            call = service?.PlusCart( map,"Bearer " + auth)!!
        }

        (call as Call<PlusCart_Response>).enqueue(object : Callback, retrofit2.Callback<PlusCart_Response> {
            override fun onResponse(
                call: Call<PlusCart_Response>,
                response: Response<PlusCart_Response>
            ) {

                if (response.code() == 200) {
                    PlusProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    PlusProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<PlusCart_Response>, t: Throwable) {
                PlusProductsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getDeleteCart(auth:String,lang:String,cart_id:String) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        map.put("cart_id",cart_id)
        var service = ApiClient.getClient()?.create(Service::class.java)
       val  call = service?.DeleteCart( map,"Bearer " + auth)!!

        call.enqueue(object : Callback, retrofit2.Callback<PlusCart_Response> {
            override fun onResponse(
                call: Call<PlusCart_Response>,
                response: Response<PlusCart_Response>
            ) {

                if (response.code() == 200) {
                    PlusProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    PlusProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<PlusCart_Response>, t: Throwable) {
                PlusProductsMutableLiveData?.setValue(null)

            }
        })
    }



}