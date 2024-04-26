package com.example.callscreenapp.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telecom.Call
import android.telecom.VideoProfile
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.callscreenapp.R
import com.example.callscreenapp.service.MyInCallServiceImplementation

@Suppress("DEPRECATION")
class MyDialerActivity : AppCompatActivity() {

    private lateinit var callStateReceiver: BroadcastReceiver
    private var currentCall: Call? = null

    override fun onStart() {
        super.onStart()
        currentCall = MyInCallServiceImplementation.currentCall
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_dialer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()
        setupReceivers()
        setClickListeners()
    }

    private fun setupUI() {
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun setupReceivers() {
        callStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.getIntExtra(MyInCallServiceImplementation.EXTRA_CALL_STATE, -1) == Call.STATE_DISCONNECTED) {
                    finish()
                }
            }
        }
        registerReceiver(callStateReceiver, IntentFilter(MyInCallServiceImplementation.ACTION_CALL_STATE_CHANGED))
    }

    private fun setClickListeners() {
        findViewById<ImageView>(R.id.btn_answer).setOnClickListener { answerCall() }
        findViewById<ImageView>(R.id.btn_reject).setOnClickListener { rejectCall() }
    }

    private fun answerCall() = currentCall?.answer(VideoProfile.STATE_AUDIO_ONLY)
    private fun rejectCall() = currentCall?.reject(false, "")



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(callStateReceiver)
    }
}