package com.example.callscreenapp.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.cardview.widget.CardView
import com.example.callscreenapp.R
import com.example.callscreenapp.ui.activity.RingtoneActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class MenuOptionFragment : Fragment() {

    companion object {
        fun newInstance() = MenuOptionFragment()
    }

    private val viewModel: MenuOptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnRingtone: CardView = view.findViewById(R.id.menu_option_ringtone)
        btnRingtone.setOnClickListener(){
            val intent = Intent(context, RingtoneActivity::class.java)
            startActivity(intent)
        }

        val btnVibrate: SwitchMaterial = view.findViewById(R.id.fragment_menu_option_vibrate)
        btnVibrate.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Log.e("btnVibrate", "btnVibrate true")
            } else {
                Log.e("btnVibrate", "btnVibrate false")
            }
        }

        val btnFlash: SwitchMaterial = view.findViewById(R.id.fragment_menu_option_flash)
        btnFlash.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Log.e("btnVibrate", "btnVibrate true")
            } else {
                Log.e("btnVibrate", "btnVibrate false")
            }
        }
    }
}