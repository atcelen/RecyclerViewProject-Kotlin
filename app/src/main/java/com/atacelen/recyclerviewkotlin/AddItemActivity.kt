package com.atacelen.recyclerviewkotlin

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_add_item.*


class AddItemActivity : AppCompatActivity() {
    var selectedImage : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
    }

    fun selectImage(view : View) {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentToGallery, 2)
        }
    }

    fun add(view : View) {
        val intentToMainActivity = Intent(this@AddItemActivity, MainActivity::class.java)
        intentToMainActivity.putExtra("intentID", "intent_from_AddItemActivity")
        intentToMainActivity.putExtra("addedFoodName", foodName.text.toString())
        intentToMainActivity.putExtra("addedFoodPrice", Integer.parseInt(foodPrice.text.toString()))

        val singleton = Singleton.Selected
        singleton.selectedImage = (selectAnImage.drawable as BitmapDrawable).bitmap

        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intentToMainActivity)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intentToGallery, 2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data

            if(selectedImage != null) {
                if(Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, selectedImage!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    selectAnImage.setImageBitmap(bitmap)
                } else {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                    selectAnImage.setImageBitmap(bitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}