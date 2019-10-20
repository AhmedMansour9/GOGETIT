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
    private var No_Product:Boolean=false

     var listProductsMutableLiveData: MutableLiveData<AllProducts_Response>? = null
    private lateinit var context: Context
     fun getFilteredData(category_id:String,Lang: String,brand_id:String,type: String,min_price:String,max_price: String
                               ,size:String, context: Context): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        this.context = context
        getProductsFiltered(category_id,Lang,brand_id,type,min_price,max_price,size)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }

     fun getData(Lang:String, context: Context): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        this.context = context
        getDataValues(Lang)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }
     fun getLatest(Lang:String, context: Context): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        this.context = context
        getLatest(Lang)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }
    fun getSimilar(Lang:String,Productid:String, context: Context): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        this.context = context
        getSimiliarProducts(Lang,Productid)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }

     fun getProductsId(category_id:String,Lang:String, context: Context): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        this.context = context
        getProductsId(category_id,Lang)
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
    private fun getSimiliarProducts(Lang: String,product_id:String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)
        map.put("product_id",product_id)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.SimiliarProducts(map)
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


    private fun getProductsId(category_id:String,Lang: String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)
        map.put("category_id",category_id)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.ProductsByCatId(map)
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



    private fun getProductsFiltered(category_id:String,Lang: String,brand_id:String,type: String,min_price:String,
                                    max_price: String ,size:String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)
        map.put("category_id",category_id)
        map.put("brand_id",brand_id)
        if(type.equals("men")){
            map.put("women","0")
            map.put("men","1")
            map.put("kid","0")
        }else  if(type.equals("woman")){
            map.put("women","1")
            map.put("men","0")
            map.put("kid","0")
        }else  if(type.equals("kid")){
            map.put("women","0")
            map.put("men","0")
            map.put("kid","1")
        }
        if(min_price!=null) {
            map.put("min_price", min_price)
        }
        if(max_price!=null) {
            map.put("max_price", max_price)
        }
        map.put("size",size)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.FilterProducts(map)
        call?.enqueue(object : Callback, retrofit2.Callback<AllProducts_Response> {
            override fun onResponse(call: Call<AllProducts_Response>, response: Response<AllProducts_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    No_Product=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AllProducts_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    fun getStatus():Boolean{

        return No_Product
    }

}