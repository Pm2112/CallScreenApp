package com.example.callscreenapp.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class DownLoadImage {

    fun loadImageIntoView(context: Context, imageView: ImageView, imageUrl: String) {
        Glide.with(context)
            .load(imageUrl)
            .into(imageView)
    }
}