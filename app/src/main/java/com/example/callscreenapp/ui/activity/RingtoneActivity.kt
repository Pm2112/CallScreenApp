package com.example.callscreenapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.callscreenapp.R
import com.example.callscreenapp.adapter.ListRingtoneAdapter
import com.example.callscreenapp.adapter.ListTopicAdapter
import com.example.callscreenapp.model.ListRingtone
import com.example.callscreenapp.model.ListTopic

class RingtoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(R.layout.activity_ringtone)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ringtone_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listRingtone: RecyclerView = findViewById(R.id.ringtone_activity_list_ringtone)
        listRingtone.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        // Tạo danh sách mẫu
        val nameRingtone = listOf(
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1"),
            ListRingtone("Ringtones 1")
        )
        listRingtone.adapter = ListRingtoneAdapter(nameRingtone)

        val btnBack: ImageView = findViewById(R.id.ringtone_activity_icon_back)
        btnBack.setOnClickListener(){
            val intent = Intent(this, ShowImageActivity::class.java)
            startActivity(intent)
        }

        val btnSubmit: ImageView = findViewById(R.id.ringtone_activity_icon_submit)
        btnSubmit.setOnClickListener(){
            val intent = Intent(this, ShowImageActivity::class.java)
            startActivity(intent)
        }
    }
}