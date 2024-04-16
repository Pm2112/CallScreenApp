package com.example.callscreenapp.ui.fragment.call_themes

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.callscreenapp.R
import com.example.callscreenapp.adapter.ListTopicAdapter
import com.example.callscreenapp.adapter.PhoneCallListImageAdapter
import com.example.callscreenapp.model.ListTopic
import com.example.callscreenapp.model.PhoneCallListImage
import com.example.callscreenapp.ui.activity.SettingActivity
import com.example.callscreenapp.ui.fragment.premission.PremissionSheetFragment
import com.example.callscreenapp.ui.fragment.setting.SettingFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class CallThemesFragment : Fragment() {

    companion object {
        fun newInstance() = CallThemesFragment()
    }

    private val viewModel: CallThemesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return inflater.inflate(R.layout.fragment_call_themes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listTopic: RecyclerView = view.findViewById(R.id.call_themes_fragment_list_topic)
        val listImageView: RecyclerView = view.findViewById(R.id.call_themes_fragment_list_image)
        listTopic.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(listTopic)

        // Tạo danh sách mẫu
        val nameTopic = listOf(
            ListTopic("Anime", R.drawable.icon_topic),
            ListTopic("Love", R.drawable.icon_topic),
            ListTopic("Neon", R.drawable.icon_topic),
            ListTopic("Anime", R.drawable.icon_topic),
            ListTopic("Anime", R.drawable.icon_topic),
            ListTopic("Anime", R.drawable.icon_topic),
            ListTopic("Anime", R.drawable.icon_topic),
            ListTopic("Anime", R.drawable.icon_topic),
            ListTopic("Anime", R.drawable.icon_topic)
        )
        listTopic.adapter = ListTopicAdapter(nameTopic)

        // Kích hoạt PermissionSheetFragment khi nhấn vào một nút
        val btnShowPermissions: ImageView =
            view.findViewById(R.id.call_themes_fragment_icon_setting)
        btnShowPermissions.setOnClickListener {
//            val intent = Intent(context, SettingActivity::class.java)
//            startActivity(intent)
            showPermissionSheet()
        }

        val listImage = listOf(
            PhoneCallListImage("https://i1-dulich.vnecdn.net/2021/07/16/1-1626437591.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=yCCOAE_oJHG0iGnTDNgAEA"),
            PhoneCallListImage("https://i1-dulich.vnecdn.net/2021/07/16/1-1626437591.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=yCCOAE_oJHG0iGnTDNgAEA"),
            PhoneCallListImage("https://i1-dulich.vnecdn.net/2021/07/16/1-1626437591.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=yCCOAE_oJHG0iGnTDNgAEA"),
            PhoneCallListImage("https://i1-dulich.vnecdn.net/2021/07/16/1-1626437591.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=yCCOAE_oJHG0iGnTDNgAEA"),
            PhoneCallListImage("https://i1-dulich.vnecdn.net/2021/07/16/1-1626437591.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=yCCOAE_oJHG0iGnTDNgAEA"),
            PhoneCallListImage("https://i1-dulich.vnecdn.net/2021/07/16/1-1626437591.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=yCCOAE_oJHG0iGnTDNgAEA"),
            PhoneCallListImage("https://i1-dulich.vnecdn.net/2021/07/16/1-1626437591.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=yCCOAE_oJHG0iGnTDNgAEA"),
            PhoneCallListImage("https://i1-dulich.vnecdn.net/2021/07/16/1-1626437591.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=yCCOAE_oJHG0iGnTDNgAEA")
        )

        val layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_BETWEEN
        }
        listImageView.layoutManager = layoutManager

        listImageView.adapter = PhoneCallListImageAdapter(listImage)
    }

    private fun showPermissionSheet() {
        val permissionSheet = PremissionSheetFragment()
        permissionSheet.show(childFragmentManager, permissionSheet.tag)
    }


}