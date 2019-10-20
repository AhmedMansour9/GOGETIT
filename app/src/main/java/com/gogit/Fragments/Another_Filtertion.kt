package com.gogit.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gogit.Adapter.*
import com.gogit.Model.AllSizes_Response
import com.gogit.Model.Brand_Response
import com.gogit.Model.Categories_Response
import com.gogit.Model.Sizes_Response

import com.gogit.R
import com.gogit.View.onClickFilter_View
import com.gogit.ViewModel.Brands_ViewModel
import com.gogit.ViewModel.Categories_ViewModel
import com.gogit.ViewModel.Sizes_ViewModel
import kotlinx.android.synthetic.main.fragment_another__filtertion.view.*
import kotlinx.android.synthetic.main.fragment_another__filtertion.view.recycler_Sizes
import kotlinx.android.synthetic.main.fragment_details__products.view.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class Another_Filtertion : Fragment() , onClickFilter_View {
    lateinit var size:String
    lateinit var root:View
    lateinit var Type:String
    lateinit var brand_id:String
    lateinit var category_id:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_another__filtertion, container, false)
        getData()
         getAllCategories()
        getBrands()
        getSizes()
        Filter()

    return root
    }

    fun Filter(){
        root.Btn_Filter.setOnClickListener(){
            if(!root.T_min.text.toString().isEmpty()){
                if(!root.T_Max.toString().isEmpty()){

                var minprice:Int=root.T_min.text.toString().toInt()
                var maxprice:Int=root.T_Max.text.toString().toInt()
                 if(maxprice<minprice){
                     Toast.makeText(
                         context!!.applicationContext,
                         context!!.applicationContext.getString(R.string.selecttruemaxprice),
                         Toast.LENGTH_LONG
                     ).show()
                 }else {
                     peformFilter()
                 }

                }else {
                    peformFilter()
                }

            }else {
                peformFilter()
            }




        }
    }
    fun peformFilter(){
        if(brand_id==null){
            Toast.makeText(
                context!!.applicationContext,
                context!!.applicationContext.getString(R.string.selectbrand),
                Toast.LENGTH_LONG
            ).show()
        }else  if(category_id==null){
            Toast.makeText(
                context!!.applicationContext,
                context!!.applicationContext.getString(R.string.selectcategory),
                Toast.LENGTH_LONG
            ).show()
        }else {
            var productsByid=ResultProducts_Fragment()
            val bundle = Bundle()
            bundle.putString("type", Type)
            bundle.putString("brand_id", brand_id)
            bundle.putString("category_id", category_id)
            bundle.putString("size", size)
            bundle.putString("min_price", root.T_min.text.toString())
            bundle.putString("maxprice", root.T_Max.text.toString())
            productsByid.arguments=bundle
            fragmentManager?.popBackStack()
            fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, productsByid)?.addToBackStack(null)?.commit()

        }
    }
    fun getSizes(){
        var Sizes: Sizes_ViewModel = ViewModelProviders.of(this)[Sizes_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            Sizes.getAllSizes("en", it).observe(this, Observer<AllSizes_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val listAdapter  = AllSizes_Adapter(context!!.applicationContext,loginmodel.data)
                    listAdapter.onClickProductSize(this)
                    root.recycler_Sizes.layoutManager = LinearLayoutManager(this.context!!.applicationContext, LinearLayoutManager.HORIZONTAL ,false)
                    root.recycler_Sizes.setAdapter(listAdapter)
                }


            })
        }

    }
    fun getAllCategories(){
        var categories: Categories_ViewModel = ViewModelProviders.of(this)[Categories_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            categories.getCategories("en", it).observe(this, Observer<Categories_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val listAdapter =
                        CategoriesSelected_Adapter(context!!.applicationContext, loginmodel.data)
                    listAdapter.onClickCategoryId(this)
                    root.Recycle_Categories.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    root.Recycle_Categories.setAdapter(listAdapter)
                }

            })
        }
    }
    fun getBrands(){
        var brands: Brands_ViewModel= ViewModelProviders.of(this)[Brands_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            brands.getCategories("en", it).observe(this, Observer<Brand_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val listAdapter =
                        Brands_Adapter(context!!.applicationContext, loginmodel.data)
                    listAdapter.onClickBrandId(this)
                    root.Recycle_Brands.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false)
                    root.Recycle_Brands.setAdapter(listAdapter)
                }

            })
        }
    }

    fun getData(){
        var bundle: Bundle? =this.arguments
        if (bundle != null) {
            Type= bundle.getString("type")!!
        }
        if(Type.equals("men")){
            root.T_Title.text=root.resources.getString(R.string.men)
        }else if(Type.equals("woman")){
            root.T_Title.text=root.resources.getString(R.string.woman)
            }
        else {
            root.T_Title.text=root.resources.getString(R.string.kids)
        }

    }

    override fun category_id(category_id: String) {
    this.category_id=category_id
    }

    override fun size(sizes: String) {
        size=sizes
    }

    override fun Brand_id(brandid: String) {
     brand_id=brandid
    }

}
