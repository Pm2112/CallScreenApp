package com.example.callscreenapp.process

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.FileOutputStream
import java.io.InputStream

fun saveBitmapToFile(context: Context, bitmap: Bitmap, filename: String) {
    val outputStream: FileOutputStream
    try {
        // Mở một file output stream để viết vào bộ nhớ nội bộ
        outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream) // Bitmap is compressed by 100% here
        outputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun getBitmapFromGallery(context: Context, uri: Uri): Bitmap? {
    var inputStream: InputStream? = null
    return try {
        inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        inputStream?.close()
    }
}

fun displayImageFromInternalStorage(context: Context, imageView: ImageView, filename: String) {
    try {
        val fileInputStream = context.openFileInput(filename)
        val bitmap = BitmapFactory.decodeStream(fileInputStream)
        imageView.setImageBitmap(bitmap)
        fileInputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}