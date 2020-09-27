package com.atacelen.recyclerviewkotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuRecyclerAdapter(private val foodNameList : ArrayList<String>, private val priceList : ArrayList<Int>, private val foodImages : ArrayList<Bitmap>) : RecyclerView.Adapter<MenuRecyclerAdapter.MenuItemHolder>() {

    /*
     LayoutInflaters are used to instantiate xml files into their corresponding View Objects.
     Inflating means adding a view to an activity during runtime
     In this example we inflate a view for the recycler_view_row xml file during the runtime of this program.

     Documentation:
    https://developer.android.com/reference/kotlin/android/view/LayoutInflater
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_row, parent, false)
        return MenuItemHolder(view)
    }


    override fun getItemCount(): Int {
        return foodNameList.size
    }

    // We set the texts and the image of our MenuItemHolder object
    override fun onBindViewHolder(holder: MenuItemHolder, position: Int) {
        holder.foodNameText?.text = foodNameList[position]
        holder.priceText?.text = priceList[position].toString() + " CHF"
        holder.imageView?.setImageBitmap(foodImages[position])
        holder.linearLayout?.setBackgroundResource(R.drawable.box_ui)
    }


    /*
        "A ViewHolder describes an item view and metadata about its place within the RecyclerView."

        https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder
    */

    class MenuItemHolder(view : View) : RecyclerView.ViewHolder(view) {
        var imageView : ImageView? = null
        var foodNameText : TextView? = null
        var priceText : TextView? = null
        var linearLayout : LinearLayout? = null

        // links the attributes with the recycler_view_row items
        init {
            imageView = view.findViewById(R.id.recycler_row_imageView)
            foodNameText = view.findViewById(R.id.recycler_row_foodNameText)
            priceText = view.findViewById(R.id.recycler_row_priceText)
            linearLayout = view.findViewById(R.id.linearLayout)
        }
    }
}