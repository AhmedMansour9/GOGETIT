package com.gogit.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gogit.Model.AllSizes_Response
import com.gogit.Model.Sizes_Response
import com.gogit.R
import com.gogit.View.OnClickProductColorView
import com.gogit.View.ProductBytUd_View
import com.gogit.View.onClickFilter_View

class AllSizes_Adapter  (context: Context, val userList: List<AllSizes_Response.Data>)
    : RecyclerView.Adapter<AllSizes_Adapter.ViewHolder>() {
    lateinit var productbyid: onClickFilter_View
    var row_index:Int = 0
    var context: Context =context
    lateinit var  onClickProductSizesView: OnClickProductColorView
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllSizes_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_sizes, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: AllSizes_Adapter.ViewHolder, position: Int) {
        holder.T_Size.text=userList.get(position).title
        if (row_index == position) {
            holder.T_Size.setBackground(context.getResources().getDrawable(R.drawable.bc_sizeblack))
            holder.T_Size.setTextColor(Color.parseColor("#FFFFFF"));
            this.productbyid.size(
                userList.get(position).title
            )
        } else {
            holder.T_Size.setBackground(context.getResources().getDrawable(R.drawable.bc_sizewhite))
            holder.T_Size.setTextColor(Color.parseColor("#c9c9c9"));
        }
        holder.T_Size.setOnClickListener(){
            row_index = position
            this.productbyid.size(
                userList.get(position).title
            )

            notifyDataSetChanged()
        }
    }
    fun onClickProductSize(onClickProductColorView: onClickFilter_View) {
        this.productbyid = onClickProductColorView
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context
        var T_Size = itemView.findViewById(R.id.T_Size) as TextView
        var relativeLayout = itemView.findViewById(R.id.rel_around_circle) as RelativeLayout

        fun bindItems(dataModel: Sizes_Response.Data) {


        }
    }
}