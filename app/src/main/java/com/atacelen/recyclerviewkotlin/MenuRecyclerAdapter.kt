package com.atacelen.recyclerviewkotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuRecyclerAdapter(private val foodNameList : ArrayList<String>, private val priceList : ArrayList<Int>, private val foodImages : ArrayList<Bitmap>) : RecyclerView.Adapter<MenuRecyclerAdapter.MenuItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_row, parent, false)
        return MenuItemHolder(view)
    }

    override fun getItemCount(): Int {
        return foodNameList.size
    }

    override fun onBindViewHolder(holder: MenuItemHolder, position: Int) {
        holder.foodNameText?.text = foodNameList[position]
        holder.priceText?.text = priceList[position].toString() + " CHF"
        holder.imageView?.setImageBitmap(foodImages[position])
    }

    class MenuItemHolder(view : View) : RecyclerView.ViewHolder(view) {
        var imageView : ImageView? = null
        var foodNameText : TextView? = null
        var priceText : TextView? = null

        init {
            imageView = view.findViewById(R.id.imageView)
            foodNameText = view.findViewById(R.id.recycler_row_foodNameText)
            priceText = view.findViewById(R.id.recycler_row_foodPrice)
        }
    }
}