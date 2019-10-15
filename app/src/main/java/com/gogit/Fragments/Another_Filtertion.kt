package com.gogit.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gogit.Adapter.CategoriesSelected_Adapter
import com.gogit.Adapter.Categories_Adapter
import com.gogit.Model.Categories_Response

import com.gogit.R
import com.gogit.ViewModel.Categories_ViewModel
import kotlinx.android.synthetic.main.fragment_another__filtertion.view.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class Another_Filtertion : Fragment() {
    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_another__filtertion, container, false)

         getAllCategories()

    return root
    }
    fun getAllCategories(){
        var categories: Categories_ViewModel = ViewModelProviders.of(this)[Categories_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            categories.getCategories("en", it).observe(this, Observer<Categories_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val listAdapter =
                        CategoriesSelected_Adapter(context!!.applicationContext, loginmodel.data)
//                    listAdapter.onClick(this)
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
    fun getData(){
        var bundle: Bundle? =this.arguments
        var title:String?=bundle!!.getString("type")
        if(title.equals("men")){
            root.T_Title.text=root.resources.getString(R.string.men)
        }

    }

}
