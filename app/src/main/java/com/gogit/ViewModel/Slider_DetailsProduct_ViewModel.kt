package com.gogit.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gogit.Model.SliderHome_Model
import com.gogit.Model.Slider_DetailsProduct
import com.gogit.Retrofit.ApiClient
import com.gogit.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Slider_DetailsProduct_ViewModel: ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Slider_DetailsProduct>? = null
    private lateinit var context: Context


    public fun getData(Id:String,Lang:String, context: Context): LiveData<Slider_DetailsProduct> {
        listProductsMutableLiveData = MutableLiveData<Slider_DetailsProduct>()
        this.context = context
        getDataValues(Id,Lang)
        return listProductsMutableLiveData as MutableLiveData<Slider_DetailsProduct>
    }


    private fun getDataValues(Id:String,Lang: String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)
        map.put("product_id",Id)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.SliderProducts(map)
        call?.enqueue(object : Callback, retrofit2.Callback<Slider_DetailsProduct> {
            override fun onResponse(call: Call<Slider_DetailsProduct>, response: Response<Slider_DetailsProduct>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Slider_DetailsProduct>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
}