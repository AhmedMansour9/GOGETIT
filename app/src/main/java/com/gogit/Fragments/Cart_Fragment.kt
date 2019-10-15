package com.gogit.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gogit.Adapter.Cart_Adapter
import com.gogit.Model.Cart_Response
import com.gogit.Model.PlusCart_Response
import com.gogit.View.PlusId_View

import com.gogit.R
import com.gogit.ViewModel.Cart_ViewModel
import kotlinx.android.synthetic.main.fragment_cart_.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class Cart_Fragment : Fragment(), PlusId_View ,SwipeRefreshLayout.OnRefreshListener{


    lateinit var root:View
    private lateinit var DataSaver: SharedPreferences
    lateinit var UserToken: String
     var T_TotalPrice:String?=null
    lateinit var allproducts: Cart_ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root= inflater.inflate(R.layout.fragment_cart_, container, false)
        allproducts = ViewModelProviders.of(this)[Cart_ViewModel::class.java]
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!
        SwipRefresh()
        getAllCart()
        checkOrder()

      return root
    }

    private fun checkOrder() {
        root.Btn_checkout.setOnClickListener(){
            var productsByid=OrderLocation_Fragmet()
            val bundle = Bundle()
            bundle.putString("totalprice", T_TotalPrice!!)
            productsByid.arguments=bundle
            fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, productsByid)
                ?.addToBackStack(null)?.commit()
        }


    }

    fun getAllCart(){
        root.SwipCart.isRefreshing= true
        this.context!!.applicationContext?.let {
            allproducts.getData(UserToken,"en", it).observe(this, Observer<Cart_Response> { loginmodel ->
                root.SwipCart.isRefreshing=false
                if(loginmodel!=null) {
                    T_TotalPrice=loginmodel.data.price.toString()
                    root.contrain_checkout.visibility=View.VISIBLE
                    root.T_Tax.text=resources.getString(R.string.tax)+"$"+loginmodel.data.totalTax.toString()
                    root.T_Deleivery.text=resources.getString(R.string.delivery)+"$"+loginmodel.data.totalDeleveryFees
                    root.TotalPrice.text=resources.getString(R.string.total)+"$"+loginmodel.data.price.toString()
                    val listAdapter =
                        Cart_Adapter(context!!.applicationContext, loginmodel.data.list)
                    listAdapter.OnClickPlus(this)
                    root.Recycle_Cart.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext, LinearLayoutManager.VERTICAL
                        , false
                    )
                    root.Recycle_Cart.setAdapter(listAdapter)
                }else {
                    root.empty_cart.visibility=View.VISIBLE
                    root.contrain_checkout.visibility=View.GONE
                    root.Recycle_Cart.visibility=View.GONE
                }

            })
        }
    }

    override fun minus_Id(Id: String) {
        this.context!!.applicationContext?.let {
            allproducts.AddPlusData(UserToken,"en",Id,"minus", it).observe(this, Observer<PlusCart_Response> { loginmodel ->
                if(loginmodel!=null) {
                    getAllCart()
                }

            })
        }
    }

    override fun Plus_Id(Id: String) {
        this.context!!.applicationContext?.let {
            allproducts.AddPlusData(UserToken,"en",Id,"plus", it).observe(this, Observer<PlusCart_Response> { loginmodel ->
                if(loginmodel!=null) {
                    getAllCart()
                }

            })
        }
    }

    fun SwipRefresh(){
        root.SwipCart.setOnRefreshListener(this)
        root.SwipCart.isRefreshing=true
        root.SwipCart.isEnabled = true
        root.SwipCart.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        root.SwipCart.post(Runnable {
           getAllCart()

        })
    }
    override fun onRefresh() {
        getAllCart()

    }
    override fun delete(Id: String) {

        this.context!!.applicationContext?.let {
            allproducts.DeleteData(UserToken,"en",Id, it).observe(this, Observer<PlusCart_Response> { loginmodel ->
                if(loginmodel!=null) {
                    getAllCart()
                }

            })
        }
    }}
