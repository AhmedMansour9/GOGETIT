package com.gogit.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gogit.Model.Categories_Response
import com.gogit.Model.Sizes_Response
import com.gogit.R
import com.gogit.View.OnClickProductColorView
import com.gogit.View.ProductBytUd_View
import com.gogit.View.onClickFilter_View
import kotlinx.android.synthetic.main.item_categoryfilter.view.*

class CategoriesSelected_Adapter (context: Context, val userList: List<Categories_Response.CategoriesDetails>)
    : RecyclerView.Adapter<CategoriesSelected_Adapter.ViewHolder>() {
    lateinit var productbyid: onClickFilter_View
    var row_index:Int = 0
    var context: Context =context
    lateinit var  onClickProductSizesView: OnClickProductColorView
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesSelected_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_categoryfilter, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: CategoriesSelected_Adapter.ViewHolder, position: Int) {
        holder.itemView.T_Title.text=userList.get(position).title
        if (row_index == position) {
            holder.itemView.img_status.visibility=View.VISIBLE
            this.productbyid.category_id(
                userList.get(position).id.toString()
            )
        } else {
            holder.itemView.img_status.visibility=View.GONE

        }
        holder.itemView.setOnClickListener(){
            row_index = position
            this.productbyid.category_id(
                userList.get(position).id.toString()
            )
            notifyDataSetChanged()
        }
    }
    fun onClickCategoryId(onClickProductColorView: onClickFilter_View) {
        this.productbyid = onClickProductColorView
    }

    fun onClickProductSize(onClickProductColorView: OnClickProductColorView) {
        this.onClickProductSizesView = onClickProductColorView
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val context: Context = itemView.context
//        var T_Size = itemView.findViewById(R.id.T_Size) as TextView
//        var relativeLayout = itemView.findViewById(R.id.rel_around_circle) as RelativeLayout

        fun bindItems(dataModel: Sizes_Response.Data) {


        }
    }
}