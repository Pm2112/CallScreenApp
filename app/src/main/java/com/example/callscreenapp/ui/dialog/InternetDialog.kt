package com.example.callscreenapp.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.TextView
import com.example.callscreenapp.R

public fun showNetworkCustomDialog(context: Context) {
    // Tạo builder cho dialog
    val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_internet, null)
    val builder = AlertDialog.Builder(context)
    builder.setView(dialogView)
    val alertDialog = builder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


    dialogView.findViewById<TextView>(R.id.dialog_title).text = "Lost internet connection"
    dialogView.findViewById<TextView>(R.id.dialog_message).text = "You need to reconnect to the network to continue using"
    // Thiết lập các sự kiện click cho các nút trong dialog

    alertDialog.show() // Hiển thị dialog
}