package com.example.callscreenapp.ui.fragment.my_themes

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.callscreenapp.R
import com.example.callscreenapp.database.AppDatabase
import com.example.callscreenapp.database.DataImage

class MyThemesFragment : Fragment() {

    companion object {
        fun newInstance() = MyThemesFragment()
    }

    private val viewModel: MyThemesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_my_themes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val db = AppDatabase.getDatabase(requireContext())
//        val dataImageDao = db.dataImage()
//
//        // Thêm user
//        dataImageDao.insertAll(DataImage(urlBackGround = "John Doe", urlIconCall = 30))
//
//        // Lấy tất cả users
//        val listImage = dataImageDao.getAll()
//        Log.e("TAGlistImage", listImage.toString())
    }
}