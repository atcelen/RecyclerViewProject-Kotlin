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

    /*
        This method will be called when the user clicks on the screen to select an Image from the user's gallery.
        Due to privacy reasons, we should however first ask the user for permission to access its storage (as shown in the if-clause)
        If this permission is given once, we don't have to ask the user for permission again and can call an intent with a picking action to reach the gallery and pick an image.
        To be able to access the external storage at all, the line "<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>" needs to be added to the Android Manifest!!
        Check the "AndroidManifest.xml" file under manifests
        The requestCodes are used to distinguish between the method calls.
     */
    fun selectImage(view : View) {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentToGallery, 2)
        }
    }

    /*
        The "add" method is called , when the button is clicked on (check the OnClick method for the "add" button)
        The food name and price information are put into the intent, which is then used to go back to the Main Activity.
        The information inserted into the intent with "putExtra" is extracted in the Main Activity
        We carry the images using the Singleton object
     */
    fun add(view : View) {
        val intentToMainActivity = Intent(this@AddItemActivity, MainActivity::class.java)
        intentToMainActivity.putExtra("intentID", "intent_from_AddItemActivity")
        intentToMainActivity.putExtra("addedFoodName", foodName.text.toString())
        intentToMainActivity.putExtra("addedFoodPrice", Integer.parseInt(foodPrice.text.toString()))

        val singleton = Singleton.Selected
        singleton.selectedImage = (selectAnImage.drawable as BitmapDrawable).bitmap

        // The "setFlags" method with "Intent.FLAG_ACTIVITY_CLEAR_TOP" closes all the previous activities
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intentToMainActivity)


    }
    /*
        The "onRequestPermissionResult" method is called for the result of the "requestPermissions" method
        If the permission is given, we call the same code block as the "else" code block from the "selectImage" method.
        It should be checked that the grantResults array is not empty in order to prevent a reference to a null object and also that the permission is given.
        The requestCode has to be checked as well because in larger scale projects there might be multiple permission requests with different requestCodes.
     */
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
/*
    The "onActivityResult" method is called for the result of the "startActivityForResult" method
    The "getBitmap" method in the "else" block of the method has deprecated with API 29, however many devices still use lower APIs.
    Therefore we adjust the code in the way that it calls certain methods depending on the API level of the device.
    The newer way of turning the URI into a bitmap is by creating a "ImageDecoder.Source" object and decoding it into a bitmap

    "A Uniform Resource Identifier (URI) is a string of characters that unambiguously identifies a particular resource."
    https://en.wikipedia.org/wiki/Uniform_Resource_Identifier

    For more Information on "onActivityResult":
    https://developer.android.com/training/basics/intents/result
 */
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