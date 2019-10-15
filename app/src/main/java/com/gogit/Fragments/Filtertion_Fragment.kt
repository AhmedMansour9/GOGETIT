package com.gogit.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gogit.R
import kotlinx.android.synthetic.main.fragment_filtertion.view.*

/**
 * A simple [Fragment] subclass.
 */
class Filtertion_Fragment : Fragment() {
   lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_filtertion, container, false)
        openMens()

        return root
    }

    fun openMens(){
        root.constrain_Men.setOnClickListener(){
            var productsByid=Another_Filtertion()
            val bundle = Bundle()
            bundle.putString("type", "men")
            productsByid.arguments=bundle
            fragmentManager?.beginTransaction()?.replace(R.id.Constrain_Home, productsByid)?.commit()

        }
    }


}
