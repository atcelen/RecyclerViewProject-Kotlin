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
    var foodNameList : ArrayList<String> = ArrayList()
    var priceList : ArrayList<Int> = ArrayList()
    var foodImages : ArrayList<Bitmap> = ArrayList()

    var adapter : MenuRecyclerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = MenuRecyclerAdapter(foodNameList, priceList, foodImages)
        recyclerView.adapter = adapter

        val intent : Intent? = intent
        if(intent != null && intent.getStringExtra("intentID") == "intent_from_AddItemActivity") {
            foodNameList.add(intent.getStringExtra("addedFoodName").toString())
            priceList.add(intent.getIntExtra("addedFoodPrice", 0))

            val singleton = Singleton.Selected
            foodImages.add(singleton.selectedImage!!)

            adapter!!.notifyDataSetChanged()

        }


        
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_food_item) {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}