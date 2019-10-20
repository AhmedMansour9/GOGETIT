package com.gogit.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.gogit.Activties.Navigation
import com.gogit.Adapter.AllProducts_Adapter
import com.gogit.Model.AllProducts_Response
import com.gogit.Model.Categories_Response

import com.gogit.R
import com.gogit.View.ProductDetails_View
import com.gogit.ViewModel.getAllProducts_ViewModel
import kotlinx.android.synthetic.main.fragment_all_products_.*
import kotlinx.android.synthetic.main.fragment_all_products_.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProductsById_Fragment : Fragment() , ProductDetails_View {
     lateinit var root:View
    lateinit var DeviceLang:String

    var bundle=Bundle()
    lateinit var categories: Categories_Response.CategoriesDetails
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_products_by_id_, container, false)
         init()
        Language()
        getFilterProducts()

        return root
    }
    fun getFilterProducts(){

        bundle = this!!.arguments!!
        categories = bundle.getParcelable("ProductItem")!!
        root.T_AllProduct.text=categories.title
        var allproducts: getAllProducts_ViewModel = ViewModelProviders.of(this)[getAllProducts_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getProductsId(categories.id.toString(),DeviceLang, it)?.observe(this, Observer<AllProducts_Response> { loginmodel ->
               if(loginmodel.data!=null) {
                   val listAdapter =
                       AllProducts_Adapter(context!!.applicationContext, loginmodel.data)
                   listAdapter.onClick(this)
                   Recycle_AllShoes.setLayoutManager(
                       GridLayoutManager(
                           context!!.applicationContext
                           , 2
                       )
                   )
                   Recycle_AllShoes.setAdapter(listAdapter)
               }

            })
        }
    }

    fun init() {

        val toggle = ActionBarDrawerToggle(
            activity,
            Navigation.drawerLayout,
            root.toolbarProducts,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.icon_menu)
            setToolbarNavigationClickListener { Navigation.drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        Navigation.drawerLayout?.addDrawerListener(toggle)

    }
    override fun Details(detailsProduct: AllProducts_Response.AllProducts_Model) {
        var productsByid=Details_ProductsFragment()
        val bundle = Bundle()
        bundle.putParcelable("ProductItem", detailsProduct)
        productsByid.arguments=bundle
        fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, productsByid)
            ?.addToBackStack(null)?.commit()

    }

    fun Language() {
        DeviceLang = Locale.getDefault().language
    }
}
