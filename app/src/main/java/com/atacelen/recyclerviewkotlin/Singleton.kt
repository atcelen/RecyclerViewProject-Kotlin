package com.atacelen.recyclerviewkotlin

import android.graphics.Bitmap

/*
    A Singleton class is a class that can only hold one instance. Therefore all Singleton instances refer to the same Singleton object.
    A companion object ties a function or a property to a class rather than its instances.
    The companion object is the property of the class and is initialized once the class is loaded.
    A class can only have one companion object

    To see more details about Companion Objects:
    https://kotlinlang.org/docs/tutorials/kotlin-for-py/objects-and-companion-objects.html

    Side note : We don't have the "static" keyword in Kotlin. As a consequence, we cannot build the Singleton class as we did in the Java version

 */
class Singleton {
    companion object Selected {
        var selectedImage : Bitmap? = null
    }
}