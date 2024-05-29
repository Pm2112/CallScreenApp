package com.example.callscreenapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.callscreenapp.Impl.NetworkChangeListener
import com.example.callscreenapp.R
import com.example.callscreenapp.redux.store.store
import com.example.callscreenapp.ui.dialog.RateAppDialog
import com.example.callscreenapp.ui.dialog.showNetworkCustomDialog

class NotificationActivity : AppCompatActivity(), NetworkChangeListener {

    private lateinit var rateAppDialog: RateAppDialog

    override fun onNetworkUnavailable() {
        runOnUiThread {
            showNetworkCustomDialog(this)
        }
    }

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

        rateAppDialog = RateAppDialog(this, this)

        val urlItem = store.state.backgroundUrl
        urlItem.let {
            // Sử dụng Glide để tải hình ảnh và đặt làm background
            val imageView = findViewById<ImageView>(R.id.notification_background)
            Glide.with(this)
                .load(it)
                .centerCrop() // Sử dụng centerCrop để giữ tỷ lệ của hình ảnh
                .into(imageView)
        }

        val avatarImage: ImageView = findViewById(R.id.notification_background_avatar)
        val avatarUrl = store.state.avatarUrl
        Glide.with(this)
            .load(avatarUrl)
            .centerCrop() // Sử dụng centerCrop để giữ tỷ lệ của hình ảnh
            .into(avatarImage)

        val btnAnswer: ImageView = findViewById(R.id.notification_background_icon_green)
        val iconAnswer = store.state.iconCallShowGreen
        val btnReject: ImageView = findViewById(R.id.notification_background_icon_red)
        val iconReject = store.state.iconCallShowRed

        Glide.with(this)
            .load(iconAnswer)
            .centerCrop() // Sử dụng centerCrop để giữ tỷ lệ của hình ảnh
            .into(btnAnswer)
        Glide.with(this)
            .load(iconReject)
            .centerCrop() // Sử dụng centerCrop để giữ tỷ lệ của hình ảnh
            .into(btnReject)

        val btnHome: ImageView = findViewById(R.id.notification_activity_btn)
        btnHome.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        rateAppDialog.show()
    }

    override fun onBackPressed() {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
    }
}