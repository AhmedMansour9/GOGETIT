package com.gogit.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gogit.Activties.Navigation
import com.gogit.Adapter.AllProducts_Adapter
import com.gogit.Adapter.MyOrders_Adapter
import com.gogit.Model.AllProducts_Response
import com.gogit.Model.MyOrders_Response
import com.gogit.Model.Orders_Response

import com.gogit.R
import com.gogit.View.DetailsMyOrdersView
import com.gogit.ViewModel.MyOrders_ViewModel
import com.gogit.ViewModel.getAllProducts_ViewModel
import kotlinx.android.synthetic.main.fragment_all_products_.*
import kotlinx.android.synthetic.main.fragment_editprofile.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_orders.view.*
import kotlinx.android.synthetic.main.fragment_result_products_.view.*

/**
 * A simple [Fragment] subclass.
 */
class MyOrders_Fragment : Fragment() , DetailsMyOrdersView {


    lateinit var root:View
    lateinit var UserToken: String
    private lateinit var DataSaver: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_my_orders, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!
        init()

        getAllOrders()


        return root
    }
    fun init() {

        val toggle = ActionBarDrawerToggle(
            activity,
            Navigation.drawerLayout,
            root.toolbarorders,
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


    fun getAllOrders(){
        var allproducts: MyOrders_ViewModel = ViewModelProviders.of(this)[MyOrders_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getData(UserToken, it)?.observe(this, Observer<MyOrders_Response> { loginmodel ->
                if(loginmodel!=null) {
                    root.view3.visibility=View.VISIBLE
                    root.T_TitleOrders.visibility=View.VISIBLE
                    root.T_NUmberOrders.text=loginmodel.data.size.toString()
                    val listAdapter =
                        MyOrders_Adapter(context!!.applicationContext, loginmodel.data)
                    listAdapter.OnClick(this)
                    root.Recycle_Order.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    root.Recycle_Order.setAdapter(listAdapter)
                }else {
                    var status:Boolean=allproducts.getStatus()
                    if(status){
                        root.Recycle_Order.visibility=View.GONE
                        root.T_NoOrder.visibility=View.VISIBLE
                    }

                }

            })
        }
    }

    override fun showDetailsMyOrders(myOrdersData: MyOrders_Response.Data) {
        val detailsMyOrdersFragment = Details_OrdersFragment()
        val bundle = Bundle()
        bundle.putParcelable("MyOrdersItem", myOrdersData)
        detailsMyOrdersFragment.setArguments(bundle)
        fragmentManager!!.beginTransaction()
            .replace(R.id.Rela_Home, detailsMyOrdersFragment)
            .addToBackStack(null).commit()
    }


}
