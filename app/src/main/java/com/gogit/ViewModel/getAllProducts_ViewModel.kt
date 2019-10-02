package com.gogit.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gogit.Model.AllProducts_Response
import com.gogit.Model.SliderHome_Model
import com.gogit.Retrofit.ApiClient
import com.gogit.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class getAllProducts_ViewModel: ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<AllProducts_Response>? = null
    private lateinit var context: Context


    public fun getData(Lang:String, context: Context): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        this.context = context
        getDataValues(Lang)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }
    public fun getLatest(Lang:String, context: Context): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        this.context = context
        getLatest(Lang)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }
    public fun getProductsId(ProductId:String,Lang:String, context: Context): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        this.context = context
        getLatest(Lang)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }

    private fun getDataValues(Lang: String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.AllProducts(map)
        call?.enqueue(object : Callback, retrofit2.Callback<AllProducts_Response> {
            override fun onResponse(call: Call<AllProducts_Response>, response: Response<AllProducts_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AllProducts_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getLatest(Lang: String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.LatestProducts(map)
        call?.enqueue(object : Callback, retrofit2.Callback<AllProducts_Response> {
            override fun onResponse(call: Call<AllProducts_Response>, response: Response<AllProducts_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AllProducts_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getProductsId(ProductId:String,Lang: String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.LatestProducts(map)
        call?.enqueue(object : Callback, retrofit2.Callback<AllProducts_Response> {
            override fun onResponse(call: Call<AllProducts_Response>, response: Response<AllProducts_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AllProducts_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
}