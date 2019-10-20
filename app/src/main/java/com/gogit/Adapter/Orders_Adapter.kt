package com.gogit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gogit.Model.Categories_Response
import com.gogit.Model.MyOrders_Response
import com.gogit.Model.Order_Response
import com.gogit.Model.Orders_Response
import com.gogit.R
import com.gogit.View.DetailsMyOrdersView
import com.gogit.View.ProductBytUd_View

class Orders_Adapter  (context: Context, val userList: List<Orders_Response.Data>)
: RecyclerView.Adapter<Orders_Adapter.ViewHolder>() {
    lateinit var productbyid: ProductBytUd_View
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Orders_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_category, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Orders_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    fun onClick(productI: ProductBytUd_View){
        this.productbyid=productI
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context

        fun bindItems(dataModel: Orders_Response.Data) {
            val img = itemView.findViewById(R.id.imag_product) as ImageView


        }
    }
}
