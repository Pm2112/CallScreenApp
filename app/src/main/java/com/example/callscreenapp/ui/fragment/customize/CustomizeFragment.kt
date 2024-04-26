package com.example.callscreenapp.ui.fragment.customize

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.callscreenapp.R
import com.example.callscreenapp.adapter.ListAvatarAdapter
import com.example.callscreenapp.adapter.ListCallButtonAdapter
import com.example.callscreenapp.adapter.ListTopicAdapter
import com.example.callscreenapp.adapter.PhoneCallListImageAdapter
import com.example.callscreenapp.data.ListAvatar
import com.example.callscreenapp.data.ListButtonCall
import com.example.callscreenapp.model.ListAvatar
import com.example.callscreenapp.model.ListCallButton
import com.example.callscreenapp.model.ListTopic
import com.example.callscreenapp.model.PhoneCallListImage
import com.example.callscreenapp.process.getBitmapFromGallery
import com.example.callscreenapp.process.saveBitmapToFile
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import java.io.File
import java.io.FileNotFoundException
import java.io.OutputStream

class CustomizeFragment : Fragment() {

    companion object {
        fun newInstance() = CustomizeFragment()
    }

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    private val viewModel: CustomizeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_customize, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAvatar: RecyclerView = view.findViewById(R.id.customize_fragment_list_avatar)
        listAvatar.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(listTopic)

        // Tạo danh sách mẫu
        val urlAvatar = ListAvatar
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Log.d("ImagePicker", "Selected Image URI: $uri")
                val imageBitmap = getBitmapFromGallery(requireContext(), it)
                imageBitmap?.let { it1 -> saveBitmapToFile(requireContext(), it1, "demo1") }

                Log.d("ImagePickerBitmap", "Selected Image URI: $imageBitmap")
            }
        }



        listAvatar.adapter = ListAvatarAdapter(urlAvatar, pickImageLauncher)

        val listCallIcon: RecyclerView = view.findViewById(R.id.customize_fragment_list_icon_call)
        listCallIcon.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(listCallIcon)

        // Tạo danh sách mẫu
        val nameIconCall = ListButtonCall
        listCallIcon.adapter = ListCallButtonAdapter(nameIconCall)

        val listImageView: RecyclerView = view.findViewById(R.id.customize_fragment_list_background_image)
        val listImage = listOf(
            PhoneCallListImage("content://media/picker/0/com.android.providers.media.photopicker/media/1000000137"),
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

}