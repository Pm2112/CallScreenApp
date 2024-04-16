package com.example.callscreenapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.callscreenapp.R

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notification)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val urlItem = intent.getStringExtra("URL_ITEM")
        urlItem.let {
            // Sử dụng Glide để tải hình ảnh và đặt làm background
            val imageView = findViewById<ImageView>(R.id.notification_background)
            Glide.with(this)
                .load(it)
                .centerCrop() // Sử dụng centerCrop để giữ tỷ lệ của hình ảnh
                .into(imageView)
        }

        val btnHome: ImageView = findViewById(R.id.notification_activity_btn)
        btnHome.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}