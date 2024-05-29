package com.example.callscreenapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.callscreenapp.Impl.NetworkChangeListener
import com.example.callscreenapp.R
import com.example.callscreenapp.ui.dialog.RateAppDialog
import com.example.callscreenapp.ui.dialog.showNetworkCustomDialog

class SettingActivity : AppCompatActivity(), NetworkChangeListener {
    private lateinit var rateAppDialog: RateAppDialog
    override fun onNetworkUnavailable() {
        runOnUiThread {
            showNetworkCustomDialog(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rateAppDialog = RateAppDialog(this, this)

        val iconBack: ImageView = findViewById(R.id.setting_activity_icon_back)

        iconBack.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnRateApp: CardView = findViewById(R.id.rate_app)
        val btnPrivacyPolicy: CardView = findViewById(R.id.privacy_policy)
        val btnTermsOfUse: CardView = findViewById(R.id.terms_of_use)
        val btnMoreApps: CardView = findViewById(R.id.more_apps)

        btnRateApp.setOnClickListener() {
            rateAppDialog.show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}