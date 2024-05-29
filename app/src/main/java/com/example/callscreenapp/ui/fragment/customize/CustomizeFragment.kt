package com.example.callscreenapp.ui.fragment.customize

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.callscreenapp.R
import com.example.callscreenapp.adapter.CustomizeAdapter
import com.example.callscreenapp.adapter.ListAvatarAdapter
import com.example.callscreenapp.adapter.ListCallButtonAdapter
import com.example.callscreenapp.data.ListAvatar
import com.example.callscreenapp.data.ListButtonCall
import com.example.callscreenapp.data.ListCategoryAll
import com.example.callscreenapp.data.realm
import com.example.callscreenapp.database.Avatar
import com.example.callscreenapp.database.Background
import com.example.callscreenapp.database.ContactDb
import com.example.callscreenapp.model.ListAvatar
import com.example.callscreenapp.model.PhoneCallListImage
import com.example.callscreenapp.process.saveImageFromUri
import com.example.callscreenapp.redux.action.AppAction
import com.example.callscreenapp.redux.store.store
import com.example.callscreenapp.ui.activity.ShowImageActivity
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class CustomizeFragment : Fragment() {

    companion object {
        fun newInstance() = CustomizeFragment()
    }

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private lateinit var pickBackgroundLauncher: ActivityResultLauncher<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_customize, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAvatar: RecyclerView = view.findViewById(R.id.customize_fragment_list_avatar)
        listAvatar.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        store.dispatch(AppAction.SetAvatarUrl(""))

        // Tạo danh sách mẫu
        var urlAvatar = ListAvatar
        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    val imagePath = requireContext().saveImageFromUri(it)
//                    Log.d("CustomizeFragment", "URI a: $it")
                    realm.writeBlocking {
                        copyToRealm(Avatar().apply {
                            avatarUri = imagePath ?: ""
                        })
                    }

                    val items: RealmResults<Avatar> = realm.query<Avatar>().find()
                    items.last().let { item ->
//                        Log.d("CustomizeFragment", "URI a: ${item.avatarUri}")
                        urlAvatar.add(1, ListAvatar(item.avatarUri))
                    }
                    urlAvatar.forEach() {
                        Log.d("CustomizeFragment", "URI a: ${it.urlAvatar}")
                    }
                    listAvatar.adapter = ListAvatarAdapter(urlAvatar, pickImageLauncher)
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

        val listImageView: RecyclerView =
            view.findViewById(R.id.customize_fragment_list_background_image)
        val listImage = ListCategoryAll

        val layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_BETWEEN
        }
        listImageView.layoutManager = layoutManager

        pickBackgroundLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    val imagePath = requireContext().saveImageFromUri(it)
//                    Log.d("CustomizeFragment", "URI a: $it")
                    realm.writeBlocking {
                        copyToRealm(Background().apply {
                            backgroundUrl = imagePath ?: ""
                        })
                    }

                    val items: RealmResults<Background> = realm.query<Background>().find()
                    items.last().let { item ->
//                        Log.d("CustomizeFragment", "URI a: ${item.avatarUri}")
                        listImage.add(1, PhoneCallListImage(item.backgroundUrl, "", "", ""))
                    }

                    listImageView.adapter = CustomizeAdapter(listImage, childFragmentManager, pickBackgroundLauncher)
                }
            }

        listImageView.adapter =
            CustomizeAdapter(listImage, childFragmentManager, pickBackgroundLauncher)

        val btnPreview: TextView = view.findViewById(R.id.customize_fragment_preview)
        btnPreview.setOnClickListener() {
            val intent = Intent(context, ShowImageActivity::class.java)
            startActivity(intent)
        }
    }
}