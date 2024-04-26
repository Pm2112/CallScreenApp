package com.example.callscreenapp.ui.fragment.call_themes



import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.callscreenapp.R
import com.example.callscreenapp.adapter.ListTopicAdapter
import com.example.callscreenapp.adapter.PhoneCallListImageAdapter
import com.example.callscreenapp.data.ListCategoryAll
import com.example.callscreenapp.data.ListCategoryAnimal
import com.example.callscreenapp.data.ListCategoryAnime
import com.example.callscreenapp.data.ListCategoryCastle
import com.example.callscreenapp.data.ListCategoryFantasy
import com.example.callscreenapp.data.ListCategoryGame
import com.example.callscreenapp.data.ListCategoryLove
import com.example.callscreenapp.data.ListCategoryNature
import com.example.callscreenapp.data.ListCategorySea
import com.example.callscreenapp.data.ListCategoryTech
import com.example.callscreenapp.model.ListTopic
import com.example.callscreenapp.model.PhoneCallListImage
import com.example.callscreenapp.redux.store.store
import com.example.callscreenapp.ui.fragment.premission.PremissionSheetFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class CallThemesFragment : Fragment() {
    private var storeSubscription: (() -> Unit)? = null
    private var listImage: List<PhoneCallListImage> = ListCategoryAll
    companion object {
        fun newInstance() = CallThemesFragment()
    }

    private val viewModel: CallThemesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        val nameTopic = listOf(
            ListTopic("All", R.drawable.icon_topic),
            ListTopic("Anime", R.drawable.icon_topic),
            ListTopic("Animal", R.drawable.icon_topic),
            ListTopic("Love", R.drawable.icon_topic),
            ListTopic("Nature", R.drawable.icon_topic),
            ListTopic("Game", R.drawable.icon_topic),
            ListTopic("Castle", R.drawable.icon_topic),
            ListTopic("Fantasy", R.drawable.icon_topic),
            ListTopic("Tech", R.drawable.icon_topic),
            ListTopic("Sea", R.drawable.icon_topic)
        )
        listTopic.adapter = ListTopicAdapter(nameTopic)

        val btnShowPermissions: ImageView =
            view.findViewById(R.id.call_themes_fragment_icon_setting)
        btnShowPermissions.setOnClickListener {
            showPermissionSheet()
        }



        val layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_BETWEEN
        }
        listImageView.layoutManager = layoutManager

        storeSubscription = store.subscribe {
            val category = store.state.categoryName
            requireActivity().runOnUiThread {
                listImage = when (category) {
                    "All" -> ListCategoryAll
                    "Anime" -> ListCategoryAnime
                    "Animal" -> ListCategoryAnimal
                    "Love" -> ListCategoryLove
                    "Nature" -> ListCategoryNature
                    "Game" -> ListCategoryGame
                    "Castle" -> ListCategoryCastle
                    "Fantasy" -> ListCategoryFantasy
                    "Tech" -> ListCategoryTech
                    "Sea" -> ListCategorySea
                    else -> ListCategoryAll // default to All if category not found
                }
                listImageView.adapter = PhoneCallListImageAdapter(listImage)
            }
        }

        listImageView.adapter = PhoneCallListImageAdapter(listImage)
    }

    private fun showPermissionSheet() {
        val permissionSheet = PremissionSheetFragment()
        permissionSheet.show(childFragmentManager, permissionSheet.tag)
    }
}