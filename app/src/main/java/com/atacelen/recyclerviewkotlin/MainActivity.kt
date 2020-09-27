package com.atacelen.recyclerviewkotlin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view_row.*

class MainActivity : AppCompatActivity() {

    // Initializing the class attributes..
    var foodNameList : ArrayList<String> = ArrayList()
    var priceList : ArrayList<Int> = ArrayList()
    var foodImages : ArrayList<Bitmap> = ArrayList()

    var adapter : MenuRecyclerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Filling the ArrayLists..
        foodNameList.add("Quattro Formaggi")
        foodNameList.add("Ravioli")
        foodNameList.add("Paella")
        foodNameList.add("Fondue")
        foodNameList.add("Sushi")

        priceList.add(20)
        priceList.add(17)
        priceList.add(23)
        priceList.add(25)
        priceList.add(15)

        foodImages.add(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.quattro_formaggi))
        foodImages.add(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ravioli))
        foodImages.add(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.paella))
        foodImages.add(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.fondue))
        foodImages.add(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.sushi))





        /*
            "A LayoutManager is responsible for measuring and positioning item views within a RecyclerView as well as determining the policy for when to recycle item views that are no longer visible to the user.
            By changing the LayoutManager a RecyclerView can be used to implement a standard vertically scrolling list, a uniform grid, staggered grids, horizontally scrolling collections and more.
            Several stock layout managers are provided for general use."

            https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.LayoutManager

            A LinearLayoutManager is a RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
         */
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        /*
            "An Adapter object acts as a bridge between an AdapterView and the underlying data for that view.
            The Adapter provides access to the data items.
            The Adapter is also responsible for making a View for each item in the data set."

            https://developer.android.com/reference/android/widget/Adapter
         */
        adapter = MenuRecyclerAdapter(foodNameList, priceList, foodImages)
        recyclerView.adapter = adapter

        /*
            If we come to the Main Activity from the Add Item Activity(we check that by putting in an intent ID),
            we extract the data put into the intent and add them to the ArrayLists.
            In the end, we notify the adapter of the data set change so that the recycler view can update itself.
         */
        val intent : Intent? = intent
        if(intent != null && intent.getStringExtra("intentID") == "intent_from_AddItemActivity") {
            foodNameList.add(intent.getStringExtra("addedFoodName").toString())
            priceList.add(intent.getIntExtra("addedFoodPrice", 0))

            // See "Singleton.kt" for details
            val singleton = Singleton.Selected
            foodImages.add(singleton.selectedImage!!)

            adapter!!.notifyDataSetChanged()

        }


        
    }
    //To specify the options menu for an activity, we override "onCreateOptionsMenu()" and "onOptionsItemSelected()"

    /*
        In the onCreateOptionsMenu() method we inflate our menu resource "add_item" into the "menu" which is provided as a parameter
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_item, menu)
        return super.onCreateOptionsMenu(menu)
    }
    /*
        The onOptionsItemSelected() method is called when an item from the dropdown menu is selected by the user.
        In this implementation, we take the user from the Main Activity to the Add Item Activity via an intent
    */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_food_item) {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}