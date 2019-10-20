package com.gogit.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gogit.Adapter.MyOrders_Adapter
import com.gogit.Adapter.OrderDetails_Adapter
import com.gogit.Model.MyOrders_Response
import com.gogit.Model.OrderDetails_Response
import com.gogit.Model.Orders_Response

import com.gogit.R
import com.gogit.ViewModel.MyOrders_ViewModel
import kotlinx.android.synthetic.main.fragment_details__orders.view.*
import kotlinx.android.synthetic.main.fragment_my_orders.view.*

/**
 * A simple [Fragment] subclass.
 */
class Details_OrdersFragment : Fragment() {
    lateinit var root:View
    lateinit var bundle:Bundle
    lateinit var MyOrderId:String
    lateinit var UserToken: String
    lateinit var myOrdersData: MyOrders_Response.Data
    private lateinit var DataSaver: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_details__orders, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!
        getData()
      getAllProducts()

        return root
    }

    fun getData(){

          bundle = this.arguments!!
        if (bundle != null)
{
       myOrdersData = bundle.getParcelable("MyOrdersItem")!!
       MyOrderId = myOrdersData.orderId.toString()

     root.T_delivery_address.text= myOrdersData.customerStreet + " "+ myOrdersData.customerCity + " " + myOrdersData.customerCountry
    if (myOrdersData.paymentMethod.equals("1"))
   {
   root.T_payment_method.text=resources.getString(R.string.cash)
   }

    root.T_sub_total.text=resources.getString(R.string.sub_total) + " " + myOrdersData.orderTotalPrice + " $"
    root.T_tax.text=resources.getString(R.string.Tax) + ": " +myOrdersData.tax + " $"
     root.T_delivery_fees.text=resources.getString(R.string.Delivery_fees) + ": " + myOrdersData.deleveryFees + " $"
        }

    }
    fun getAllProducts(){
        var allproducts: MyOrders_ViewModel = ViewModelProviders.of(this)[MyOrders_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getorderDetails(UserToken,MyOrderId, it)?.observe(this, Observer<OrderDetails_Response> { loginmodel ->
                if(loginmodel!=null) {

                    val listAdapter =
                        OrderDetails_Adapter(context!!.applicationContext, loginmodel.data)
                    root.recycler_details_list_my_orders.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    root.recycler_details_list_my_orders.setAdapter(listAdapter)
                }else {


                }

            })
        }
    }


}
