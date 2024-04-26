package com.example.callscreenapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.callscreenapp.R
import com.example.callscreenapp.model.ListAvatar
import com.example.callscreenapp.model.ListCallButton
import com.example.callscreenapp.model.ListRingtone
import com.example.callscreenapp.model.ListTopic
import com.example.callscreenapp.redux.action.AppAction
import com.example.callscreenapp.redux.store.store

class ListRingtoneAdapter (private val nameItem: List<ListRingtone>) : RecyclerView.Adapter<ListRingtoneAdapter.ListRingtoneViewHolder>() {


    class ListRingtoneViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameRingtone: TextView = view.findViewById(R.id.list_ringtone_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRingtoneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_ringtone, parent, false)
        return ListRingtoneViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListRingtoneViewHolder, position: Int) {
        val itemTopic = nameItem[position]
        holder.nameRingtone.text = itemTopic.nameRingtone

    }

    override fun getItemCount(): Int = nameItem.size
}