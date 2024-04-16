package com.example.callscreenapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.callscreenapp.R
import com.example.callscreenapp.model.ListCallButton
import com.example.callscreenapp.model.ListTopic
import com.example.callscreenapp.redux.action.AppAction
import com.example.callscreenapp.redux.store.store

class ListCallButtonAdapter(private val itemIcon: List<ListCallButton>) :
    RecyclerView.Adapter<ListCallButtonAdapter.ListCallButtonViewHolder>() {

    private var selectedPosition = 0  // Lưu vị trí được chọn

    class ListCallButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iconCallGreen: ImageView = view.findViewById(R.id.list_call_icon_green)
        val iconCallRed: ImageView = view.findViewById(R.id.list_call_icon_red)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCallButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_call_icon, parent, false)
        return ListCallButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListCallButtonViewHolder, position: Int) {
        val itemBtn = itemIcon[position]
        Glide.with(holder.itemView.context).load(itemBtn.urlIconGreen).into(holder.iconCallGreen)
        Glide.with(holder.itemView.context).load(itemBtn.urlIconRed).into(holder.iconCallRed)

        holder.itemView.setOnClickListener {
            store.dispatch(AppAction.SetIconCallShowId(itemBtn.urlIconGreen, itemBtn.urlIconRed))
        }
    }

    override fun getItemCount(): Int = itemIcon.size
}