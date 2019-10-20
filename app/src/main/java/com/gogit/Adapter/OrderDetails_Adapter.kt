package com.gogit.Adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gogit.Model.AllProducts_Response
import com.gogit.Model.OrderDetails_Response
import com.gogit.R
import com.gogit.View.ProductDetails_View

class OrderDetails_Adapter (context: Context, val userList: List<OrderDetails_Response.Data>)
    : RecyclerView.Adapter<OrderDetails_Adapter.ViewHolder>() {
    //this method is returning the view for each item in the list
    lateinit var ProductsDetails: ProductDetails_View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetails_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_orderdetails, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: OrderDetails_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))

    }
    fun onClick(ProductsDetail: ProductDetails_View){
        this.ProductsDetails=ProductsDetail
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context

        fun bindItems(dataModel: OrderDetails_Response.Data) {
            val title = itemView.findViewById(R.id.T_Name) as TextView
            val count = itemView.findViewById(R.id.T_Count) as TextView

            val img = itemView.findViewById(R.id.img_product) as ImageView
            val price = itemView.findViewById(R.id.T_Price) as TextView
            title.text = dataModel.productName
            count.text = dataModel.productQuantity

//             pricee = Math.round(dataModel.salePrice.toDouble())
//            val Price:Int=pricee.toInt()
            price.text = dataModel.productPrice

            Glide.with(context)
                .load("http://creativityvein.com" + dataModel.image).into(img)
        }

        }
    }
