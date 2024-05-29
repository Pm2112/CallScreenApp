package com.example.callscreenapp.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.callscreenapp.Impl.NetworkChangeListener
import com.example.callscreenapp.R
import com.example.callscreenapp.adapter.ListCallButtonAdapter
import com.example.callscreenapp.adapter.ListTopicAdapter
import com.example.callscreenapp.data.ListButtonCall
import com.example.callscreenapp.model.ListCallButton
import com.example.callscreenapp.model.ListTopic
import com.example.callscreenapp.process.saveDialerData
import com.example.callscreenapp.redux.action.AppAction
import com.example.callscreenapp.redux.store.store
import com.example.callscreenapp.ui.dialog.showNetworkCustomDialog
import com.example.callscreenapp.ui.fragment.select_contact.SelectContactFragment

class ShowImageActivity : AppCompatActivity(), NetworkChangeListener {
    private var storeSubscription: (() -> Unit)? = null

    override fun onNetworkUnavailable() {
        runOnUiThread {
            showNetworkCustomDialog(this)
        }
    }

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

        val backgroundUrl = store.state.backgroundUrl
        val iconAnswer = store.state.iconCallShowGreen
        val iconReject = store.state.iconCallShowRed
        val avatarUrl = store.state.avatarUrl

        val imageView = findViewById<ImageView>(R.id.show_image_view_activity)
        Glide.with(this)
            .load(backgroundUrl)
            .centerCrop() // Sử dụng centerCrop để giữ tỷ lệ của hình ảnh
            .into(imageView)

        val imageAvatar: ImageView = findViewById(R.id.show_image_activity_avatar)
        if (avatarUrl != "") {
            Glide.with(this)
                .load(avatarUrl)
                .into(imageAvatar)
        } else {
            findViewById<ImageView>(R.id.show_image_activity_btn_edit).visibility = GONE
            Glide.with(this)
                .load(avatarUrl)
                .into(imageAvatar)
        }



        val iconCallShowGreen: ImageView = findViewById(R.id.show_image_activity_icon_green)
        val iconCallShowRed: ImageView = findViewById(R.id.show_image_activity_icon_red)
        Glide.with(this)
            .load(iconAnswer)
            .into(iconCallShowGreen)
        Glide.with(this)
            .load(iconReject)
            .into(iconCallShowRed)

        val btnBack: ImageView = findViewById(R.id.show_image_activity_btn_back)
        btnBack.setOnClickListener() {
            showCustomDialog()
        }

        val selectContactFragment = SelectContactFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.show_image_activity, selectContactFragment)
            .hide(selectContactFragment)
            .commit()
        val btnSubmit: ImageView = findViewById(R.id.show_image_activity_btn_submit)
        btnSubmit.setOnClickListener() {
            store.dispatch(AppAction.SetBackgroundUrl(backgroundUrl))
            store.dispatch(AppAction.SetIconCallShowId(iconAnswer, iconReject))
            store.dispatch(AppAction.SetAvatarUrl(avatarUrl))

            supportFragmentManager.beginTransaction()
                .show(selectContactFragment)
                .commit()
        }

        val listCallIcon: RecyclerView = findViewById(R.id.show_image_activity_list_call_icon)
        listCallIcon.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(listCallIcon)

        // Tạo danh sách mẫu
        val nameTopic = ListButtonCall
        listCallIcon.adapter = ListCallButtonAdapter(nameTopic)


        storeSubscription = store.subscribe {
            val idIconCallShowGreen = store.state.iconCallShowGreen
            val idIconCallShowRed = store.state.iconCallShowRed
            runOnUiThread {
                loadImageIntoView(this, iconCallShowGreen, idIconCallShowGreen)
                loadImageIntoView(this, iconCallShowRed, idIconCallShowRed)
            }
        }
    }

    private fun loadImageIntoView(context: Context, imageView: ImageView, imageUrl: String) {
        Glide.with(context)
            .load(imageUrl)
            .into(imageView)
    }

    private fun showCustomDialog() {
        // Tạo builder cho dialog
        val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        dialogView.findViewById<TextView>(R.id.dialog_title).text = "BACK"
        // Thiết lập các sự kiện click cho các nút trong dialog
        dialogView.findViewById<CardView>(R.id.btn_yes).setOnClickListener {
            // Thực hiện hành động khi chọn "Có"
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            alertDialog.dismiss() // Đóng dialog
        }

        dialogView.findViewById<CardView>(R.id.btn_no).setOnClickListener {
            // Đóng dialog khi chọn "Không"
            alertDialog.dismiss()
        }

        alertDialog.show() // Hiển thị dialog
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        showCustomDialog()
    }

}