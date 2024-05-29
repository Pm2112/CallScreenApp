package com.example.callscreenapp.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.callscreenapp.Impl.NetworkChangeListener
import com.example.callscreenapp.R
import com.example.callscreenapp.process.getOnboard
import com.example.callscreenapp.process.saveOnboard
import com.example.callscreenapp.redux.store.store
import com.example.callscreenapp.service.NetworkChangeReceiver
import com.example.callscreenapp.ui.dialog.showNetworkCustomDialog
import com.example.callscreenapp.ui.fragment.call_themes.CallThemesFragment
import com.example.callscreenapp.ui.fragment.customize.CustomizeFragment
import com.example.callscreenapp.ui.fragment.my_themes.MyThemesFragment


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), NetworkChangeListener {

    // Lưu trữ các fragments để tái sử dụng
    private val callThemesFragment by lazy { CallThemesFragment() }
    private val customizeFragment by lazy { CustomizeFragment() }
    private val myThemesFragment by lazy { MyThemesFragment() }


    private var storeSubscription: (() -> Unit)? = null

    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    override fun onStart() {
        super.onStart()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkChangeReceiver)
    }

    override fun onNetworkUnavailable() {
        runOnUiThread {
            showNetworkCustomDialog(this)
        }
    }



    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Gắn CallThemesFragment vào MainActivity
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CallThemesFragment.newInstance())
                .commitNow()
        }

        networkChangeReceiver = NetworkChangeReceiver(this)



//        val (onboard, onboard1) = getOnboard(this)
//        if (onboard == true) {
//            saveOnboard(this, false)
//            val intent = Intent(this, OnboardActivity::class.java)
//            startActivity(intent)
//        }

        initializeFragments()
        setupButtons()

    }

    private fun initializeFragments() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, callThemesFragment, "CallThemesFragment")
            add(R.id.fragment_container, customizeFragment, "CustomizeFragment")
            add(R.id.fragment_container, myThemesFragment, "MyThemesFragment")
            // Chỉ hiển thị CallThemesFragment khi bắt đầu
            hide(customizeFragment)
            hide(myThemesFragment)
            commit()
        }
    }

    private fun setupButtons() {

        val btnCallThemes: ConstraintLayout = findViewById(R.id.btn_call_themes)
        val btnCustomize: ConstraintLayout = findViewById(R.id.btn_customize)
        val btnMyThemes: ConstraintLayout = findViewById(R.id.btn_my_themes)
        btnCallThemes.isSelected = true
        btnCallThemes.setOnClickListener {
            showFragment(callThemesFragment)
            btnCallThemes.isSelected = true
            btnCustomize.isSelected = false
            btnMyThemes.isSelected = false
        }
        btnCustomize.setOnClickListener {
            showFragment(customizeFragment)
            btnCallThemes.isSelected = false
            btnCustomize.isSelected = true
            btnMyThemes.isSelected = false
        }
        btnMyThemes.setOnClickListener {
            showFragment(myThemesFragment)
            btnCallThemes.isSelected = false
            btnCustomize.isSelected = false
            btnMyThemes.isSelected = true
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            // Ẩn tất cả các fragments
            supportFragmentManager.fragments.forEach {
                if (it != fragment) hide(it)
            }
            // Hiển thị fragment yêu cầu
            show(fragment)
            commit()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment != callThemesFragment) {
            showFragment(callThemesFragment)
            val btnCallThemes: ConstraintLayout = findViewById(R.id.btn_call_themes)
            val btnCustomize: ConstraintLayout = findViewById(R.id.btn_customize)
            val btnMyThemes: ConstraintLayout = findViewById(R.id.btn_my_themes)
            btnCallThemes.isSelected = true
            btnCustomize.isSelected = false
            btnMyThemes.isSelected = false
        } else {
            super.onBackPressed()
        }
    }
}