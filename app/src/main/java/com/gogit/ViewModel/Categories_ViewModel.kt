package com.gogit.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gogit.Model.Categories_Response
import com.gogit.Retrofit.ApiClient
import com.gogit.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Categories_ViewModel : ViewModel() {
    public var listProductsMutableLiveData: MutableLiveData<Categories_Response>? = null
    private lateinit var context: Context


    public fun getCategories(Lang:String, context: Context): LiveData<Categories_Response> {
        listProductsMutableLiveData = MutableLiveData<Categories_Response>()
        this.context = context
        getDataValues(Lang)
        return listProductsMutableLiveData as MutableLiveData<Categories_Response>
    }


    private fun getDataValues(Lang: String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.Categories(map)
        call?.enqueue(object : Callback, retrofit2.Callback<Categories_Response> {
            override fun onResponse(call: Call<Categories_Response>, response: Response<Categories_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Categories_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

}