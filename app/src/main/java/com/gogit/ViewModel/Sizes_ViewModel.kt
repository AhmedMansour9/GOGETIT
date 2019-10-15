package com.gogit.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gogit.Model.Sizes_Response
import com.gogit.Model.SliderHome_Model
import com.gogit.Retrofit.ApiClient
import com.gogit.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Sizes_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Sizes_Response>? = null
    private lateinit var context: Context


    public fun getData(product_id:String, context: Context): LiveData<Sizes_Response> {
        listProductsMutableLiveData = MutableLiveData<Sizes_Response>()
        this.context = context
        getDataValues(product_id)
        return listProductsMutableLiveData as MutableLiveData<Sizes_Response>
    }


    private fun getDataValues(product_id: String) {
        var map= HashMap<String,String>()
        map.put("product_id",product_id)
        map.put("hash_color","#000000")

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.SizesProducts(map)
        call?.enqueue(object : Callback, retrofit2.Callback<Sizes_Response> {
            override fun onResponse(call: Call<Sizes_Response>, response: Response<Sizes_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Sizes_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
}