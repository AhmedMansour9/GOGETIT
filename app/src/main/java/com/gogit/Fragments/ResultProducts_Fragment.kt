package com.gogit.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.gogit.Adapter.AllProducts_Adapter
import com.gogit.Model.AllProducts_Response

import com.gogit.R
import com.gogit.View.ProductDetails_View
import com.gogit.ViewModel.getAllProducts_ViewModel
import kotlinx.android.synthetic.main.fragment_all_products_.*
import kotlinx.android.synthetic.main.fragment_result_products_.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ResultProducts_Fragment : Fragment(), ProductDetails_View {
    lateinit var root:View
    lateinit var size:String
    lateinit var Type:String
    lateinit var brand_id:String
    lateinit var category_id:String
    lateinit var minprice:String
    lateinit var maxprice:String
    lateinit var DeviceLang:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_result_products_, container, false)
        Language()
        getData()
        getFilteredProducts()



        return root
    }

    fun getFilteredProducts(){
        var allproducts: getAllProducts_ViewModel = ViewModelProviders.of(this)[getAllProducts_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getFilteredData(category_id,DeviceLang,brand_id,Type,minprice,maxprice,size, it)
                .observe(this, Observer<AllProducts_Response> { loginmodel ->

                if(loginmodel!=null){
                val listAdapter  = AllProducts_Adapter(context!!.applicationContext,loginmodel.data)
                listAdapter.onClick(this)
                Recycle_AllShoes.setLayoutManager(
                    GridLayoutManager(
                        context!!.applicationContext
                        ,2)
                )
                Recycle_AllShoes.setAdapter(listAdapter) }

                else{
                    var status:Boolean=allproducts.getStatus()
                    if(status){
                        Recycle_AllShoes.visibility=View.GONE
                        root.T_NoProduct.visibility=View.VISIBLE
                    }
                }

            })
        }
    }
    fun getData(){
        var bundle:Bundle=this.arguments!!
        Type=bundle.getString("type")!!
        size=bundle.getString("size")!!
        brand_id=bundle.getString("brand_id")!!
        category_id=bundle.getString("category_id")!!
        minprice=bundle.getString("min_price")!!
        maxprice=bundle.getString("maxprice")!!
        if(Type.equals("men")){
            root.T_Title.text=root.resources.getString(R.string.men)
        }else  if(Type.equals("woman")){
            root.T_Title.text=root.resources.getString(R.string.woman)
        }else  if(Type.equals("kid")){
            root.T_Title.text=root.resources.getString(R.string.kids)
        }
    }
    override fun Details(detailsProduct: AllProducts_Response.AllProducts_Model) {
        var productsByid=Details_ProductsFragment()
        val bundle = Bundle()
        bundle.putParcelable("ProductItem", detailsProduct)
        productsByid.arguments=bundle
        fragmentManager?.beginTransaction()?.add(R.id.Constrain_Home, productsByid)
            ?.addToBackStack(null)?.commit()

    }

    fun Language() {
        DeviceLang = Locale.getDefault().language
    }


}
