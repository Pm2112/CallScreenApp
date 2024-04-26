package com.example.callscreenapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.callscreenapp.R
import com.example.callscreenapp.ui.fragment.call_themes.CallThemesFragment
import com.example.callscreenapp.ui.fragment.customize.CustomizeFragment
import com.example.callscreenapp.ui.fragment.my_themes.MyThemesFragment

class MainActivity : AppCompatActivity() {

    // Lưu trữ các fragments để tái sử dụng
    private val callThemesFragment by lazy { CallThemesFragment() }
    private val customizeFragment by lazy { CustomizeFragment() }
    private val myThemesFragment by lazy { MyThemesFragment() }

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
        findViewById<View>(R.id.btn_call_themes).setOnClickListener {
            showFragment(callThemesFragment)
        }
        findViewById<View>(R.id.btn_customize).setOnClickListener {
            showFragment(customizeFragment)
        }
        findViewById<View>(R.id.btn_my_themes).setOnClickListener {
            showFragment(myThemesFragment)
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
        } else {
            super.onBackPressed()
        }
    }

}