package com.gogit.Fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.gogit.Activties.Navigation.Companion.drawerLayout
import com.gogit.Adapter.AllProducts_Adapter
import com.gogit.Adapter.Categories_Adapter
import com.gogit.Adapter.Slider_Adapter
import com.gogit.Model.AllProducts_Response
import com.gogit.Model.Categories_Response
import com.gogit.Model.SliderHome_Model
import com.gogit.R
import com.gogit.View.ProductBytUd_View
import com.gogit.ViewModel.Categories_ViewModel
import com.gogit.ViewModel.SliderHome_ViewModel
import com.gogit.ViewModel.getAllProducts_ViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() ,ProductBytUd_View{


    var toolbarHo: Toolbar?=null
    var swipeTimer:Timer?=null
    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        viewPager!!.setCurrentItem(currentPage++, true)
    }
    lateinit var root:View
    companion object {
        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         root = inflater.inflate(R.layout.fragment_home, container, false)
        toolbarHo=root.findViewById(R.id.toolbarHome)
        init()
        getSlider()
        getLatestProducts()
        getAllCategories()
        Open_AllProducts()
        return root
    }

    private fun Open_AllProducts() {

        root.constraintLayout.setOnClickListener(){
            fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home,
                AllProducts_Fragment()
            )?.addToBackStack(null)?.commit()

        }
    }

  

    fun getSlider(){
        var SliderHome:SliderHome_ViewModel= ViewModelProviders.of(this)[SliderHome_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            SliderHome.getData("en", it)?.observe(this, Observer<SliderHome_Model> { loginmodel ->
                if(loginmodel!=null) {
                    viewPager!!.adapter = this.context?.let { it1 ->
                        Slider_Adapter(
                            it1,
                            loginmodel.data as ArrayList<SliderHome_Model.Slider_Home>
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

   fun getLatestProducts(){
       var allproducts:getAllProducts_ViewModel= ViewModelProviders.of(this)[getAllProducts_ViewModel::class.java]
       this.context!!.applicationContext?.let {
           allproducts.getLatest("en", it)?.observe(this, Observer<AllProducts_Response> { loginmodel ->

               val listAdapter  = AllProducts_Adapter(context!!.applicationContext,loginmodel.data)
               Recycle_NewShoes.setLayoutManager(
                   GridLayoutManager(
                       context!!.applicationContext
                   ,2)
               )
               Recycle_NewShoes.setAdapter(listAdapter)

           })
       }
    }


    fun getAllCategories(){
        var categories:Categories_ViewModel= ViewModelProviders.of(this)[Categories_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            categories.getCategories("en", it).observe(this, Observer<Categories_Response> { loginmodel ->

                val listAdapter  = Categories_Adapter(context!!.applicationContext,loginmodel.data)
                listAdapter.onClick(this)
                Recycle_Discover.layoutManager = LinearLayoutManager(this.context!!.applicationContext, LinearLayoutManager.HORIZONTAL ,false)

                Recycle_Discover.setAdapter(listAdapter)

            })
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(Update)
        swipeTimer!!.cancel()
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(Update)
        swipeTimer!!.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacks(Update)
        swipeTimer!!.cancel()
    }

    fun init() {

        val toggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            toolbarHo,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.icon_menu)
            setToolbarNavigationClickListener { drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        drawerLayout?.addDrawerListener(toggle)

    }

    override fun Id(categories: Categories_Response.CategoriesDetails) {

    }


    private fun drawerSetup() {
//        val toggle = ActionBarDrawerToggle(
//            this,
//            drawerLayout,
//            toolbar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        toggle.apply {
//            syncState()
//            isDrawerIndicatorEnabled = false
//            setHomeAsUpIndicator(R.drawable.ic_white_slide_menu)
//            setToolbarNavigationClickListener { drawerLayout.openDrawer(GravityCompat.START) }
//        }
//        drawerLayout.addDrawerListener(toggle)
//        sideNavRecycler.apply {
//            layoutManager = LinearLayoutManager(this@HomeActivity)
//            adapter = SideNavAdapter(SideNavInteractions.setNavItems(this@HomeActivity)) {
//                drawerLayout.closeDrawer(GravityCompat.START)
//                when (it) {
//                    0 -> {
//                    }
//                    1 -> {
//                        goTo(ContactUsActivity())
//                    }
//                    2 -> {
//                        goTo(AboutAppActivity())
//                    }
//                    3 -> {
//                        share()
//                    }
//                    4 -> {
//                        if (user == null) {
//                            showDialog()
//                        } else {
//                            goTo(ProfileActivity())
//                        }
//                    }
//                    5 -> logout(AuthActivity(), name = "dest", value = "login")
//                }
//            }
        }
}