package com.example.callscreenapp.process

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

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

fun Context.saveImageFromUri(uri: Uri): String? {
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val fileName = "${System.currentTimeMillis()}.jpg"
    val directory = filesDir  // Sử dụng bộ nhớ trong
    val file = File(directory, fileName)

    var outputStream: OutputStream? = null

    try {
        outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    } finally {
        inputStream?.close()
        outputStream?.close()
    }

    return file.absolutePath
}


fun downloadImageAndSave(context: Context, imageUrl: String, fileName: String) {
    Glide.with(context)
        .asBitmap()
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.NONE) // Không sử dụng cache đĩa
        .skipMemoryCache(true) // Không sử dụng cache bộ nhớ
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Log.d("Save Image", "Image is downloaded, now saving...")
                saveBitmapToFile(context, resource, fileName)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // Thực hiện dọn dẹp nếu cần thiết
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                Log.e("Save Image", "Failed to download image.")
            }
        })
}

private fun saveBitmapToFile(context: Context, bitmap: Bitmap, fileName: String) {
    val file = File(context.filesDir, fileName)
    try {
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) // Sử dụng PNG, có thể thay bằng JPEG nếu muốn giảm dung lượng
            Log.d("Save Image", "Image saved successfully: $fileName at ${file.absolutePath}")
        }
    } catch (e: Exception) {
        Log.e("Save Image", "Error while saving image: $fileName", e)
    }
}

fun displayImageFromInternalStorage(context: Context, imageView: ImageView, fileName: String) {
    val filePath = File(context.filesDir, fileName)
    if (filePath.exists()) {
        val bitmap = BitmapFactory.decodeFile(filePath.absolutePath)
        imageView.setImageBitmap(bitmap)
        Log.d("Display Image", "Image displayed successfully from: ${filePath.absolutePath}")
    } else {
        Log.e("Display Image", "File does not exist: $fileName")
        // Bạn có thể đặt ảnh placeholder nếu file không tồn tại
    }
}