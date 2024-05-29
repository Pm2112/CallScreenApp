package com.example.callscreenapp.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.telecom.Call
import android.telecom.VideoProfile
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.callscreenapp.R
import com.example.callscreenapp.data.realm
import com.example.callscreenapp.database.ContactDb
import com.example.callscreenapp.process.displayImageFromInternalStorage
import com.example.callscreenapp.process.getDialerDataOne
import com.example.callscreenapp.process.getDialerDataTwo
import com.example.callscreenapp.redux.store.store
import com.example.callscreenapp.service.MyInCallServiceImplementation
import com.example.callscreenapp.ui.fragment.answer_call.AnswerCallFragment
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.ext.query
import kotlin.system.exitProcess

@Suppress("DEPRECATION")
class MyDialerActivity : AppCompatActivity() {

    private lateinit var callStateReceiver: BroadcastReceiver
    private var currentCall: Call? = null
    override fun onStart() {
        super.onStart()
        currentCall = MyInCallServiceImplementation.currentCall
    }

    private var callStartTime: Long = 0
    private val callTimeHandler = Handler()
    private lateinit var callTimeUpdater: Runnable

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_dialer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val itemsNew: RealmResults<ContactDb> = realm.query<ContactDb>().find()
        val phoneNumber1 = store.state.phoneNumber

        val iconAvatar: ImageView = findViewById(R.id.avatar_image)
        val image: ImageView = findViewById(R.id.dialer_image)
        val btnAnswer: ImageView = findViewById(R.id.btn_answer)
        val btnReject: ImageView = findViewById(R.id.btn_reject)
        val phoneNumber: TextView = findViewById(R.id.phone_number)
        val phoneName: TextView = findViewById(R.id.phone_name)
        var checkContact = false
        var itemContact = ContactDb()

        itemsNew.forEach { item ->
            if (item.contactPhone == phoneNumber1) {
                checkContact = true
                itemContact = item
            }
        }

        when (checkContact) {
            true -> {
//                Glide.with(this).load(itemContact.avatarTheme).into(iconAvatar)
//                Glide.with(this).load(itemContact.backgroundTheme).into(image)
//                Glide.with(this).load(itemContact.btnAnswer).into(btnAnswer)
//                Glide.with(this).load(itemContact.btnReject).into(btnReject)
                displayImageFromInternalStorage(this, iconAvatar, itemContact.avatarTheme)
                displayImageFromInternalStorage(this, image, itemContact.backgroundTheme)
                displayImageFromInternalStorage(this, btnAnswer, itemContact.btnAnswer)
                displayImageFromInternalStorage(this, btnReject, itemContact.btnReject)
                phoneName.text = itemContact.contactName
                phoneNumber.text = itemContact.contactPhone
            }
            false -> {
                displayImageFromInternalStorage(this, iconAvatar, "avatar_theme")
                displayImageFromInternalStorage(this, image, "background_theme")
                displayImageFromInternalStorage(this, btnAnswer, "icon_answer")
                displayImageFromInternalStorage(this, btnReject, "icon_reject")
                phoneNumber.text = phoneNumber1
                phoneName.visibility = GONE
            }
        }

        setupUI()
        setupReceivers()
        setClickListeners()

        val btnRejectCall: ImageView = findViewById(R.id.btn_reject_call)
        btnRejectCall.setOnClickListener(){
            endCall()
        }

    }

    private fun setupUI() {
        supportActionBar?.hide()
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupReceivers() {
        callStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val state = intent?.getIntExtra(MyInCallServiceImplementation.EXTRA_CALL_STATE, -1)
                Log.d("MyDialerActivity1", "Received call state: $state")
                if (state == Call.STATE_DISCONNECTED) {
                    Log.d("MyDialerActivity1", "Call disconnected, finishing activity.")
                    finish() // Kết thúc activity khi cuộc gọi bị ngắt
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                callStateReceiver,
                IntentFilter(MyInCallServiceImplementation.ACTION_CALL_STATE_CHANGED),
                RECEIVER_NOT_EXPORTED
            )
        }
    }

    private fun setClickListeners() {
        findViewById<ImageView>(R.id.btn_answer).setOnClickListener {
            answerCall()
            startCallTimeUpdater()
            val btnRejectCall: ImageView = findViewById(R.id.btn_reject_call)
            val btnReject: ImageView = findViewById(R.id.btn_reject)
            val btnAnswer: ImageView = findViewById(R.id.btn_answer)
            val btnMute: ImageView = findViewById(R.id.btn_mute)
            val btnVolume: ImageView = findViewById(R.id.btn_volume)
            btnMute.visibility = VISIBLE
            btnVolume.visibility = VISIBLE
            btnReject.visibility = GONE
            btnRejectCall.visibility = VISIBLE
            btnAnswer.visibility = GONE
        }
        findViewById<ImageView>(R.id.btn_reject).setOnClickListener { rejectCall() }
    }

    private fun answerCall() = currentCall?.answer(VideoProfile.STATE_AUDIO_ONLY)
    private fun rejectCall() {
        currentCall?.reject(false, "")
        finish()
        exitProcess(0)
    }

    private fun endCall() {
        currentCall?.disconnect()
        stopCallTimeUpdater()  // Dừng cập nhật thời gian cuộc gọi
        finish()
        exitProcess(0)
    }

    private fun startCallTimeUpdater() {
        callStartTime = SystemClock.elapsedRealtime()
        callTimeUpdater = object : Runnable {
            override fun run() {
                val elapsedTime = SystemClock.elapsedRealtime() - callStartTime
                updateCallTime(elapsedTime)
                callTimeHandler.postDelayed(this, 1000)
            }
        }
        callTimeHandler.postDelayed(callTimeUpdater, 0)
    }

    private fun updateCallTime(timeInMillis: Long) {
        val seconds = (timeInMillis / 1000) % 60
        val minutes = (timeInMillis / 60000) % 60
        val hours = timeInMillis / 3600000
        val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        val callTimeTextView: TextView = findViewById(R.id.call_time)
        callTimeTextView.text = timeString
    }

    private fun stopCallTimeUpdater() {
        callTimeHandler.removeCallbacks(callTimeUpdater)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(callStateReceiver)
    }
}