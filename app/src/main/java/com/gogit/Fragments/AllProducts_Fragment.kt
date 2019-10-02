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
import com.gogit.R
import com.gogit.ViewModel.getAllProducts_ViewModel
import kotlinx.android.synthetic.main.fragment_all_products_.*
import kotlinx.android.synthetic.main.fragment_all_products_.view.*

/**
 * A simple [Fragment] subclass.
 */
class AllProducts_Fragment : Fragment() {
    lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         root= inflater.inflate(R.layout.fragment_all_products_, container, false)
        getAllProducts()
        init()

        return root;
    }

    fun getAllProducts(){
        var allproducts: getAllProducts_ViewModel = ViewModelProviders.of(this)[getAllProducts_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getData("en", it)?.observe(this, Observer<AllProducts_Response> { loginmodel ->
                val listAdapter  = AllProducts_Adapter(context!!.applicationContext,loginmodel.data)
                Recycle_AllShoes.setLayoutManager(
                    GridLayoutManager(
                        context!!.applicationContext
                        ,2)
                )
                Recycle_AllShoes.setAdapter(listAdapter)

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

}
