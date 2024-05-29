package com.example.callscreenapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.callscreenapp.R
import com.example.callscreenapp.process.getOnboard
import com.example.callscreenapp.process.saveOnboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashCustomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_custom)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000L) // Delay of 1 second

            val (onboard, onboard1) = getOnboard(this@SplashCustomActivity)
            if (onboard == true) {
                saveOnboard(this@SplashCustomActivity, false)
                val intent = Intent(this@SplashCustomActivity, OnboardActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashCustomActivity, MainActivity::class.java)
                startActivity(intent)
            }

            finish() // Finish the splash activity
        }
    }
}