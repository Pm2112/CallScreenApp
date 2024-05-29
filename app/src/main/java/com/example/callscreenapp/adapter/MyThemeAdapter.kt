package com.example.callscreenapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.callscreenapp.R
import com.example.callscreenapp.data.realm
import com.example.callscreenapp.database.BackgroundTheme
import com.example.callscreenapp.model.MyTheme
import com.example.callscreenapp.redux.action.AppAction
import com.example.callscreenapp.redux.store.store
import com.example.callscreenapp.ui.activity.MainActivity
import com.example.callscreenapp.ui.activity.ShowImageActivity
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults


class MyThemeAdapter(
    private val images: List<MyTheme>,
    private val context: Context
) :
    RecyclerView.Adapter<MyThemeAdapter.MyThemeViewHolder>() {


    private var selectedPosition = RecyclerView.NO_POSITION  // Lưu vị trí được chọn

    class MyThemeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.list_call_theme_image)
        val iconAnswer: ImageView = view.findViewById(R.id.list_call_theme_icon_answer)
        val iconReject: ImageView = view.findViewById(R.id.list_call_theme_icon_reject)
        val btnDelete: ImageView = view.findViewById(R.id.my_theme_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyThemeViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_my_theme, parent, false)
        return MyThemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyThemeViewHolder, position: Int) {
        val imageItem = images[position]
        val currentPosition = holder.adapterPosition
        Glide.with(holder.itemView.context).load(imageItem.backgroundUrl).into(holder.imageView)
        Glide.with(holder.itemView.context).load(imageItem.iconAnswer).into(holder.iconAnswer)
        Glide.with(holder.itemView.context).load(imageItem.iconReject).into(holder.iconReject)

        holder.itemView.setOnClickListener {


            val intent = Intent(holder.itemView.context, ShowImageActivity::class.java)
            store.dispatch(AppAction.SetAvatarUrl(imageItem.avatarUrl))
            store.dispatch(AppAction.SetBackgroundUrl(imageItem.backgroundUrl))
            store.dispatch(AppAction.SetIconCallShowId(imageItem.iconAnswer, imageItem.iconReject))
            holder.itemView.context.startActivity(intent)
        }


        holder.btnDelete.setOnClickListener() {
            showCustomDialog(imageItem)
        }
    }

    private fun showCustomDialog(imageItem: MyTheme) {
        // Tạo builder cho dialog
        val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        dialogView.findViewById<TextView>(R.id.dialog_title).text = "DELETE"
        // Thiết lập các sự kiện click cho các nút trong dialog
        dialogView.findViewById<CardView>(R.id.btn_yes).setOnClickListener {
            // Thực hiện hành động khi chọn "Có"
            val result = realm.query<BackgroundTheme>("backgroundTheme == $0", imageItem.backgroundUrl).find().firstOrNull()
            realm.writeBlocking {
                // Get the live frog object with findLatest(), then delete it
                if (result != null) {
                    findLatest(result)
                        ?.also { delete(it) }
                }
            }
            store.dispatch(AppAction.Refresh(true))
            alertDialog.dismiss()
        }

        dialogView.findViewById<CardView>(R.id.btn_no).setOnClickListener {
            // Đóng dialog khi chọn "Không"
            alertDialog.dismiss()
        }

        alertDialog.show() // Hiển thị dialog
    }

    override fun getItemCount() = images.size
}