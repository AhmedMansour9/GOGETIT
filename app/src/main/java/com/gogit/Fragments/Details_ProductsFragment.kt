package com.gogit.Fragments

import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.gogit.Adapter.Slider_Adapter
import com.gogit.Model.AllProducts_Response
import com.gogit.Model.Slider_DetailsProduct
import com.gogit.R
import com.gogit.ViewModel.Slider_DetailsProduct_ViewModel
import kotlinx.android.synthetic.main.fragment_details__products.*
import kotlinx.android.synthetic.main.fragment_details__products.view.*
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gogit.Adapter.Categories_Adapter
import com.gogit.Adapter.Sizes_Adapter
import com.gogit.Adapter.SliderProducts_Adapter
import com.gogit.Model.AddToCart_Response
import com.gogit.Model.Sizes_Response
import com.gogit.View.OnClickProductColorView
import com.gogit.ViewModel.AddToCart_ViewModel
import com.gogit.ViewModel.Sizes_ViewModel
import kotlinx.android.synthetic.main.fragment_details__products.viewPager
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class Details_ProductsFragment : Fragment(), OnClickProductColorView {

    lateinit var UserToken: String
    private lateinit var DataSaver: SharedPreferences
     var Size_id:String?=null
    lateinit var root: View
    var swipeTimer: Timer?=null
    private var currentPage = 0
    private var NUM_PAGES = 0
    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        viewPager!!.setCurrentItem(currentPage++, true)
    }
    var bundle= Bundle()
    lateinit var details: AllProducts_Response.AllProducts_Model
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_details__products, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!

        getData()
        getSlider()
        getSizes()
        AddToCart()
        return root
    }
    fun AddToCart(){
        root.Btn_AddToCart.setOnClickListener(){
            if(Size_id!=null) {
                root.prograss_cart.visibility = VISIBLE
                var addtocart: AddToCart_ViewModel =
                    ViewModelProviders.of(this)[AddToCart_ViewModel::class.java]
                this.context!!.applicationContext?.let {
                    addtocart.getData(UserToken, details.id.toString(), "1", "", Size_id!!, it)
                        .observe(this, Observer<AddToCart_Response> { loginmodel ->
                            root.prograss_cart.visibility =GONE
                            if (loginmodel != null) {
                                Toast.makeText(
                                    context!!.applicationContext,
                                    loginmodel.data,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                }
            }else {
                Toast.makeText(
                    context!!.applicationContext,
                    "Please  Select Size",
                    Toast.LENGTH_SHORT
                ).show()            }
        }
    }

    fun getData(){
        bundle = this.arguments!!
        details = bundle.getParcelable("ProductItem")!!
        root.T_Discription.text=details.description
        root.T_Brand.text=details.brandName
        root.T_title.text=details.title
            if (details.salePrice!=null) {
                if (details.salePrice.equals("0")) {
                    root.T_Price.text = "$" + details.priceGeneral
                } else {
                    root.T_Price.text = "$" + details.salePrice
                    root.T_OrignalPrice.text = "$" + details.priceGeneral
                    root.T_OrignalPrice.setPaintFlags(root.T_OrignalPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                }
            }
            else {
                root.T_Price.text = "$" + details.priceGeneral
            }

    }
    fun getSlider(){
        var SliderHome: Slider_DetailsProduct_ViewModel = ViewModelProviders.of(this)[Slider_DetailsProduct_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            SliderHome.getData(details.id.toString(),"en", it).observe(this, Observer<Slider_DetailsProduct> { loginmodel ->
                if(loginmodel!=null) {
                    root.viewPager!!.adapter = this.context?.let { it1 ->
                        SliderProducts_Adapter(
                            it1,
                            loginmodel.data as ArrayList<Slider_DetailsProduct.Data>
                        )
                    }

                    NUM_PAGES = loginmodel.data!!.size
                    swipeTimer = Timer()
                    swipeTimer!!.schedule(object : TimerTask() {
                        override fun run() {
                            handler.post(Update)
                        }
                    }, 3000, 3000)
                }
            })
        }
    }
    fun getSizes(){
        var Sizes: Sizes_ViewModel = ViewModelProviders.of(this)[Sizes_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            Sizes.getData(details.id.toString(), it).observe(this, Observer<Sizes_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val listAdapter  = Sizes_Adapter(context!!.applicationContext,loginmodel.data)
                    listAdapter.onClickProductSize(this)
                    root.recycler_Sizes.layoutManager = LinearLayoutManager(this.context!!.applicationContext, LinearLayoutManager.HORIZONTAL ,false)

                    root.recycler_Sizes.setAdapter(listAdapter)
                    }


            })
        }

    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(Update)
        if(swipeTimer!=null) {
            swipeTimer!!.cancel()
        }
    }

    override fun onStop() {
        super.onStop()
        if(swipeTimer!=null) {
            swipeTimer!!.cancel()
        }

        handler.removeCallbacks(Update)
    }

    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacks(Update)
        if(swipeTimer!=null) {
            swipeTimer!!.cancel()
        }
    }
    override fun showOnClickProductSizeResult(detailsProductColorsData: Sizes_Response.Data) {
     Size_id=detailsProductColorsData.size
    }
}
