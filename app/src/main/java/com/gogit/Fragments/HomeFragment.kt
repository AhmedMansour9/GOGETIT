package com.gogit.Fragments

import android.content.Context
import android.content.SharedPreferences
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
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.gogit.Activties.Navigation.Companion.drawerLayout
import com.gogit.Adapter.AllProducts_Adapter
import com.gogit.Adapter.Cart_Adapter
import com.gogit.Adapter.Categories_Adapter
import com.gogit.Adapter.Slider_Adapter
import com.gogit.Model.AllProducts_Response
import com.gogit.Model.Cart_Response
import com.gogit.Model.Categories_Response
import com.gogit.Model.SliderHome_Model
import com.gogit.R
import com.gogit.View.ProductBytUd_View
import com.gogit.View.ProductDetails_View
import com.gogit.ViewModel.Cart_ViewModel
import com.gogit.ViewModel.Categories_ViewModel
import com.gogit.ViewModel.SliderHome_ViewModel
import com.gogit.ViewModel.getAllProducts_ViewModel
import kotlinx.android.synthetic.main.fragment_cart_.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import kotlin.collections.ArrayList



class HomeFragment : Fragment() ,ProductBytUd_View, ProductDetails_View , SwipeRefreshLayout.OnRefreshListener{
    var isVisited:Boolean = false

    private lateinit var DataSaver: SharedPreferences
    lateinit var allproducts: Cart_ViewModel
    lateinit var DeviceLang:String
    var toolbarHo: Toolbar?=null
    var swipeTimer:Timer?=null
    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        viewPager!!.setCurrentItem(currentPage++, true)
    }
    var UserToken: String?=null
    lateinit var root:View
        private var currentPage = 0
        private var NUM_PAGES = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         root = inflater.inflate(R.layout.fragment_home, container, false)
        toolbarHo=root.findViewById(R.id.toolbarHome)
        init()
        Language()
        SwipRefresh()
        Open_AllProducts()
        openCart()
        openFiltertion()
        return root
    }
    fun getNumberOfCart(){
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!
        allproducts = ViewModelProviders.of(this)[Cart_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getData(UserToken!!,"en", it).observe(this, Observer<Cart_Response> { loginmodel ->

                if(loginmodel!=null) {
                root.T_notification_num.visibility=View.VISIBLE
                root.T_notification_num.text=loginmodel.data.list.size.toString()

                }

            })
        }

    }
    fun openCart(){
        root.icon_cart.setOnClickListener(){
            var cart=Cart_Fragment()
            val bundle = Bundle()
            cart.arguments=bundle
            fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, cart)
                ?.addToBackStack(null)?.commit()
        }


    }
    fun openFiltertion(){
        root.img_Filter.setOnClickListener(){
            var cart=Filtertion_Fragment()
            val bundle = Bundle()
            cart.arguments=bundle
            fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, cart)
                ?.addToBackStack(null)?.commit()
        }


    }

    private fun Open_AllProducts() {

        root.T_AllProduct.setOnClickListener(){
            fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home,
                AllProducts_Fragment()
            )?.addToBackStack(null)?.commit()

        }
    }
     fun SwipRefresh(){
         root.SwipHome.setOnRefreshListener(this)
         root.SwipHome.isRefreshing=true
         root.SwipHome.isEnabled = true
         root.SwipHome.setColorSchemeResources(
             R.color.colorPrimary,
             android.R.color.holo_green_dark,
             android.R.color.holo_orange_dark,
             android.R.color.holo_blue_dark
         )
         root.SwipHome.post(Runnable {
             getSlider()
             getLatestProducts()
             getAllCategories()
             getNumberOfCart()

         })
     }


    fun getSlider(){
        var SliderHome:SliderHome_ViewModel= ViewModelProviders.of(this)[SliderHome_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            SliderHome.getData(DeviceLang, it)?.observe(this, Observer<SliderHome_Model> { loginmodel ->
                root.SwipHome.isRefreshing=false
                if(loginmodel!=null) {
                    viewPager!!.adapter = this.context?.let { it1 ->
                        Slider_Adapter(
                            it1,
                            loginmodel.data as ArrayList<SliderHome_Model.Slider_Home>
                        )
                    }
                    root.view_pager_circle_indicator.setViewPager(viewPager)

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
           allproducts.getLatest(DeviceLang, it)?.observe(this, Observer<AllProducts_Response> { loginmodel ->
             if(loginmodel!=null) {
                 var listAdapter =
                     AllProducts_Adapter(context!!.applicationContext, loginmodel.data)
                 listAdapter.onClick(this)
                 Recycle_NewShoes.setLayoutManager(
                     GridLayoutManager(
                         context!!.applicationContext
                         , 2
                     )
                 )
                 Recycle_NewShoes.setAdapter(listAdapter)
             }
           })
       }
    }


    fun getAllCategories(){
        var categories:Categories_ViewModel= ViewModelProviders.of(this)[Categories_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            categories.getCategories(DeviceLang, it).observe(this, Observer<Categories_Response> { loginmodel ->
              if(loginmodel!=null) {
                  val listAdapter =
                      Categories_Adapter(context!!.applicationContext, loginmodel.data)
                  listAdapter.onClick(this)
                  Recycle_Discover.layoutManager = LinearLayoutManager(
                      this.context!!.applicationContext,
                      LinearLayoutManager.HORIZONTAL,
                      false
                  )
                  Recycle_Discover.setAdapter(listAdapter)
              }

            })
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(Update)
        if(swipeTimer!=null) {
            swipeTimer!!.cancel()
        }    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(Update)
        if(swipeTimer!=null) {
            swipeTimer!!.cancel()
        }    }

    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacks(Update)
        if(swipeTimer!=null) {
            swipeTimer!!.cancel()
        }    }

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
        var productsByid=ProductsById_Fragment()
        val bundle = Bundle()
        bundle.putParcelable("ProductItem", categories)
        productsByid.arguments=bundle
        fragmentManager?.beginTransaction()?.add(R.id.Constrain_Home, productsByid)
            ?.addToBackStack(null)?.commit()

    }
    override fun Details(detailsProduct: AllProducts_Response.AllProducts_Model) {
        var productsByid=Details_ProductsFragment()
        val bundle = Bundle()
        bundle.putParcelable("ProductItem", detailsProduct)
        productsByid.arguments=bundle
        fragmentManager?.beginTransaction()?.add(R.id.Constrain_Home, productsByid)
            ?.addToBackStack(null)?.commit()

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

    override fun onRefresh() {
        root.SwipHome.isRefreshing=true
        getSlider()
        getLatestProducts()
        getAllCategories()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getNumberOfCart()
    }



    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            getNumberOfCart()
        }
    }
    override fun onResume() {
        super.onResume()

    }

    fun Language() {
         DeviceLang = Locale.getDefault().language
    }
}