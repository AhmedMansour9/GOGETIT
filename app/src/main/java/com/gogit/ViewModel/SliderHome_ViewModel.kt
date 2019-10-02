package com.gogit.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gogit.Model.Register_Model
import com.gogit.Model.SliderHome_Model
import com.gogit.Retrofit.ApiClient
import com.gogit.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SliderHome_ViewModel : ViewModel()
{
    public var listProductsMutableLiveData: MutableLiveData<SliderHome_Model>? = null
    private lateinit var context: Context


    public fun getData(Lang:String, context: Context): LiveData<SliderHome_Model> {
        listProductsMutableLiveData = MutableLiveData<SliderHome_Model>()
        this.context = context
        getDataValues(Lang)
        return listProductsMutableLiveData as MutableLiveData<SliderHome_Model>
    }


    private fun getDataValues(Lang: String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.SLider(map)
        call?.enqueue(object : Callback, retrofit2.Callback<SliderHome_Model> {
            override fun onResponse(call: Call<SliderHome_Model>, response: Response<SliderHome_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<SliderHome_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }


}