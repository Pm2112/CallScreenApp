package com.example.callscreenapp.image

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.graphics.Bitmap
import android.util.Log

fun downloadAndSaveImage(context: Context, imageUrl: String, fileName: String) {
    Glide.with(context)
        .asBitmap()
        .load(imageUrl)
        .addListener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>,
                isFirstResource: Boolean
            ): Boolean {
                Log.e("DownloadImageGlide", "Failed to load image", e)
                return false
            }


            override fun onResourceReady(
                resource: Bitmap,
                model: Any,
                target: Target<Bitmap>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                Log.d("DownloadImageGlide", "Image saved successfully")
                saveBitmapToFile(context, resource, fileName)
                return true
            }
        })
        .submit()
}

private fun saveBitmapToFile(context: Context, bitmap: Bitmap, fileName: String) {
    context.openFileOutput(fileName, Context.MODE_PRIVATE).use { fos ->
        if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)) {
            Log.d("DownloadImage", "Image saved successfully: $fileName")
        } else {
            Log.d("DownloadImage", "Failed to save image")
        }
        fos.flush()
    }
}