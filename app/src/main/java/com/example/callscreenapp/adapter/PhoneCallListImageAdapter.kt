package com.example.callscreenapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.callscreenapp.R
import com.example.callscreenapp.model.PhoneCallListImage
import com.example.callscreenapp.ui.activity.ShowImageActivity

class PhoneCallListImageAdapter(private val images: List<PhoneCallListImage>) :
    RecyclerView.Adapter<PhoneCallListImageAdapter.ListImageViewHolder>() {

    class ListImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.list_call_theme_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_call_themes_image, parent, false)
        return ListImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListImageViewHolder, position: Int) {
        val imageItem = images[position]
        // Sử dụng Glide để tải hình ảnh từ URL vào ImageView
        Glide.with(holder.itemView.context).load(imageItem.urlName).into(holder.imageView)

        // Đặt sự kiện click cho view của ViewHolder
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ShowImageActivity::class.java)
            // Truyền dữ liệu nếu cần, ví dụ dưới đây truyền urlItem
            intent.putExtra("URL_ITEM", imageItem.urlName)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = images.size

}