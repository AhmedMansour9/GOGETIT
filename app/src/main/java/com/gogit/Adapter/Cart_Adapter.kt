package com.gogit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gogit.Model.Cart_Response
import com.gogit.View.PlusId_View
import com.gogit.R
import kotlinx.android.synthetic.main.item_cart.view.*

class Cart_Adapter(context: Context, val userList: List<Cart_Response.Data.X>)
    : RecyclerView.Adapter<Cart_Adapter.ViewHolder>() {
    companion object
    {
        lateinit var plusId_View: PlusId_View
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cart_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Cart_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.img_delete.setOnClickListener(){
            Cart_Adapter.plusId_View.delete(userList.get(position).cartId.toString())
            userList.drop(position)
            notifyDataSetChanged()

        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    fun OnClickPlus(plusId_Vie: PlusId_View) {

    plusId_View=plusId_Vie
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context

        fun bindItems(dataModel: Cart_Response.Data.X) {

            itemView.T_Title.text = dataModel.productName
            itemView.T_Price.text=dataModel.unitPrice
            itemView.T_Count.text=dataModel.quantity
            itemView.T_Size.text=context.resources.getString(R.string.size)+": "+dataModel.size
            itemView.TotalPrice.text=dataModel.totalUnitPrice.toString()
            Glide.with(context)
                .load("http://creativityvein.com" + dataModel.image).into(itemView.img_cart)
           itemView.img_plus.setOnClickListener(){
               var count :Int= (itemView.T_Count.text as String).toInt()
               count++
               itemView.T_Count.text=count.toString()
               Cart_Adapter.plusId_View.Plus_Id(dataModel.cartId.toString())
           }
            itemView.img_minus.setOnClickListener(){
                var count = Integer.parseInt(itemView.T_Count.text.toString())
                if(count>1) {
                    count--
                    itemView.T_Count.text=count.toString()
                    Cart_Adapter.plusId_View.minus_Id(dataModel.cartId.toString())
                }
            }

        }
    }
    }
