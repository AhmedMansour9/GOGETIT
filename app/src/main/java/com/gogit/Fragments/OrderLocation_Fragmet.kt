package com.gogit.Fragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.gogit.Activties.Navigation
import com.gogit.Model.Edit_ProfileResponse
import com.gogit.Model.Order_Response

import com.gogit.R
import com.gogit.ViewModel.EditProfile_ViiewModel
import com.gogit.ViewModel.Order_ViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.*

/**
 * A simple [Fragment] subclass.
 */
class OrderLocation_Fragmet : Fragment()
{
private lateinit var DataSaver: SharedPreferences
    var Price:String?=null
    lateinit var UserToken: String
    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_order_location__fragmet, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!

        var bundle: Bundle? =this.arguments!!
        Price=bundle!!.getString("totalprice")

        Btn_Order()
        return root
    }

    fun  Btn_Order(){
        root.Btn_order.setOnClickListener(){
            var Name = root.E_Name.text.toString()
            var Phone = root.E_phone.text.toString()
            var City = root.E_City.text.toString()
            var Country = root.E_Country.text.toString()
            var Region = root.E_Region.text.toString()
            var StreetName = root.E_StrretName.text.toString()
            var B_number = root.E_BuildingNumber.text.toString()
            var F_number = root.E_floornumber.text.toString()
            var Apartment = root.E_Apartment.text.toString()

            if(Name.isEmpty()){
                root.E_Name.error = "Name required"
                root.E_Name.requestFocus()
            }
            if(Phone.isEmpty()){
                root.E_phone.error = "Phone required"
                root.E_phone.requestFocus()
            }
            if(City.isEmpty()){
                root.E_City.error = "City required"
                root.E_City.requestFocus()
            }
            if(Country.isEmpty()){
                root.E_City.error = "Country required"
                root.E_City.requestFocus()
            }
            if(Region.isEmpty()){
                root.E_Region.error = "Region required"
                root.E_Region.requestFocus()
            }
            if(StreetName.isEmpty()){
                root.E_StrretName.error = "Street Name required"
                root.E_StrretName.requestFocus()
            }
            if(B_number.isEmpty()){
                root.E_BuildingNumber.error = "Buid Number required"
                root.E_BuildingNumber.requestFocus()
            }
            if(F_number.isEmpty()){
                root.E_floornumber.error = "Floor Number required"
                root.E_floornumber.requestFocus()
            }
            if(Apartment.isEmpty()){
                root.E_Apartment.error = "Apartment Number required"
                root.E_Apartment.requestFocus()
            } else if (!Name.isEmpty() && !Phone.isEmpty() && !City.isEmpty() && !Region.isEmpty() &&
                !StreetName.isEmpty() && !B_number.isEmpty() && !F_number.isEmpty() && !Apartment.isEmpty() ) {

                if (isConnected) {
                    root.progressBarOrder.visibility=View.VISIBLE
                    root.Btn_order.isEnabled=true
                    var order:Order_ViewModel=
                        ViewModelProviders.of(this)[Order_ViewModel::class.java]
                    order.getData(
                        UserToken,
                        Price!!,
                        StreetName,
                        Phone
                        ,"0"
                        ,"0"
                        ,"1"
                        ,"1"
                        ,City
                        ,Country
                        ,StreetName
                        ,"0"
                        ,Region
                         ,B_number
                        ,F_number
                         ,Apartment
                        , context!!.applicationContext
                    ).observe(this,
                        Observer<Order_Response> { loginmodel ->
                            root.progressBarOrder.visibility=View.GONE
                            root.Btn_order.isEnabled=false
                            if (loginmodel != null) {
                                Toast.makeText(
                                    context!!.applicationContext,
                                    context!!.applicationContext.getString(R.string.ordersuccess),
                                    Toast.LENGTH_LONG).show()
                                    val intent = Intent(context!!.applicationContext, Navigation::class.java)
                                startActivity(intent)
                                activity!!.finish()

                            } else {
                                Toast.makeText(
                                    context!!.applicationContext,
                                    context!!.applicationContext.getString(R.string.failedmsg),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    )
                } else {
                    Toast.makeText(
                        context!!.applicationContext,
                        context!!.applicationContext.getString(R.string.nointernet),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    val isConnected: Boolean
        get() {
            return (context!!.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }

}
