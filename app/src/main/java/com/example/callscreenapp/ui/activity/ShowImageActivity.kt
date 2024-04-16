package com.example.callscreenapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.callscreenapp.R
import com.example.callscreenapp.adapter.ListCallButtonAdapter
import com.example.callscreenapp.adapter.ListTopicAdapter
import com.example.callscreenapp.model.ListCallButton
import com.example.callscreenapp.model.ListTopic
import com.example.callscreenapp.redux.store.store

class ShowImageActivity : AppCompatActivity() {
    private var storeSubscription: (() -> Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_image)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.show_image_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val urlItem = intent.getStringExtra("URL_ITEM")

        urlItem.let {
            // Sử dụng Glide để tải hình ảnh và đặt làm background
            val imageView = findViewById<ImageView>(R.id.show_image_view_activity)
            Glide.with(this)
                .load(it)
                .centerCrop() // Sử dụng centerCrop để giữ tỷ lệ của hình ảnh
                .into(imageView)
        }

        val btnBack: ImageView = findViewById(R.id.show_image_activity_btn_back)
        btnBack.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnSubmit: ImageView = findViewById(R.id.show_image_activity_btn_submit)
        btnSubmit.setOnClickListener() {
            val intent = Intent(this, NotificationActivity::class.java)
            intent.putExtra("URL_ITEM", urlItem)
            startActivity(intent)
        }

        val listCallIcon: RecyclerView = findViewById(R.id.show_image_activity_list_call_icon)
        listCallIcon.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(listCallIcon)

        // Tạo danh sách mẫu
        val nameTopic = listOf(
            ListCallButton(R.drawable.icon_call_green, R.drawable.icon_call_red),
            ListCallButton(R.drawable.icon_call_red, R.drawable.icon_call_red),
            ListCallButton(R.drawable.icon_call_green, R.drawable.icon_call_red),
            ListCallButton(R.drawable.icon_call_green, R.drawable.icon_call_red),
            ListCallButton(R.drawable.icon_call_green, R.drawable.icon_call_red),
            ListCallButton(R.drawable.icon_call_green, R.drawable.icon_call_red),
            ListCallButton(R.drawable.icon_call_green, R.drawable.icon_call_red),
            ListCallButton(R.drawable.icon_call_green, R.drawable.icon_call_red),
            ListCallButton(R.drawable.icon_call_green, R.drawable.icon_call_red),
            ListCallButton(R.drawable.icon_call_green, R.drawable.icon_call_red),
            ListCallButton(R.drawable.icon_call_green, R.drawable.icon_call_red)
        )
        listCallIcon.adapter = ListCallButtonAdapter(nameTopic)

        val iconCallShowGreen: ImageView = findViewById(R.id.show_image_activity_icon_green)
        val iconCallShowRed: ImageView = findViewById(R.id.show_image_activity_icon_red)
        storeSubscription = store.subscribe {
            val idIconCallShowGreen = store.state.iconCallShowGreen
            val idIconCallShowRed = store.state.iconCallShowRed
            runOnUiThread {
                loadImageIntoView(this, iconCallShowGreen, idIconCallShowGreen)
                loadImageIntoView(this, iconCallShowRed, idIconCallShowRed)
            }
        }

    }

    fun loadImageIntoView(context: Context, imageView: ImageView, imageUrl: Int) {
        Glide.with(context)
            .load(imageUrl)
            .into(imageView)
    }

}