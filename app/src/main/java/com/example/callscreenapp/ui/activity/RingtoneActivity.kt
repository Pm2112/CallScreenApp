package com.example.callscreenapp.ui.activity

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.callscreenapp.Impl.NetworkChangeListener
import com.example.callscreenapp.Impl.OnItemClickListener
import com.example.callscreenapp.R
import com.example.callscreenapp.adapter.ListRingtoneAdapter
import com.example.callscreenapp.model.ListRingtone
import com.example.callscreenapp.process.saveRingtone
import com.example.callscreenapp.ui.dialog.LoadingDialog
import com.example.callscreenapp.ui.dialog.showNetworkCustomDialog
import com.example.callscreenapp.ui.fragment.play_ringtone.PlayRingtoneFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@Suppress("DEPRECATION")
class RingtoneActivity : AppCompatActivity(), OnItemClickListener, NetworkChangeListener {

    private lateinit var btnBack: ImageView
    private lateinit var btnSubmit: ImageView
    private lateinit var listRingtone: RecyclerView
    private var selectedRingtoneUrl: String? = null
    private var selectedRingtoneName: String? = null
    private lateinit var loadingDialog: LoadingDialog

    override fun onNetworkUnavailable() {
        runOnUiThread {
            showNetworkCustomDialog(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(R.layout.activity_ringtone)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ringtone_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadingDialog = LoadingDialog(this) // Khởi tạo LoadingDialog

        listRingtone = findViewById(R.id.ringtone_activity_list_ringtone)
        listRingtone.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Tạo danh sách mẫu
        val nameRingtone = listOf(
            ListRingtone("Visualisation", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Most+Popular/visualisation.m4r"),
            ListRingtone("High Tension Buildup", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Most+Popular/hightensionbuildup.m4r"),
            ListRingtone("Incoming Call", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Most+Popular/incomingcall.m4r"),
            ListRingtone("Mobile Ringtone", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Most+Popular/mobileringtone.m4r"),
            ListRingtone("klingelton", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Most+Popular/klingelton.m4r"),
            ListRingtone("Telefono Ringtone", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Most+Popular/telefonoringtone.m4r"),
            ListRingtone("Bell Phone 4", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Most+Popular/bellphone4.m4r"),
            ListRingtone("Bell Ping 2", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Most+Popular/bellping2.m4r"),
            ListRingtone("Phone Ringing 1", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Most+Popular/phoneringing1.m4r"),
            ListRingtone("Phone Ringing 2", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Most+Popular/phoneringing2.m4r"),
            ListRingtone("Rush", "https://s3-us-west-1.amazonaws.com/com.ringtonemaker.app/Best+Ringtones/rush.m4r")
        )
        listRingtone.adapter = ListRingtoneAdapter(nameRingtone, this)

        btnBack = findViewById(R.id.ringtone_activity_icon_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, ShowImageActivity::class.java)
            startActivity(intent)
        }

        btnSubmit = findViewById(R.id.ringtone_activity_icon_submit)
        btnSubmit.setOnClickListener {
            selectedRingtoneUrl?.let { url ->
                selectedRingtoneName?.let { name ->
                    DownloadAndSaveRingtoneTask(this, url, name).execute()
                }
            }
            val intent = Intent(this, ShowImageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(position: Int, ringtoneUrl: String, nameRingtone: String) {
        setControlsEnabled(false)
        selectedRingtoneUrl = ringtoneUrl
        selectedRingtoneName = nameRingtone
        DownloadRingtoneTask(this, ringtoneUrl, nameRingtone).execute()
    }

    private fun setControlsEnabled(enabled: Boolean) {
        btnBack.isEnabled = enabled
        btnSubmit.isEnabled = enabled
        listRingtone.forEach { it.isEnabled = enabled }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(this, ShowImageActivity::class.java)
        startActivity(intent)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("StaticFieldLeak")
    private inner class DownloadRingtoneTask(
        private val context: Context,
        private val ringtoneUrl: String,
        private val ringtoneName: String
    ) : AsyncTask<Void, Void, String>() {
        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            loadingDialog.show() // Hiển thị loading dialog
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): String? {
            return try {
                val url = URL(ringtoneUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    return null
                }

                val file = File(context.cacheDir, "$ringtoneName.m4r")
                if (file.exists()) {
                    file.delete()
                }
                val outputStream = FileOutputStream(file)
                val inputStream: InputStream = connection.inputStream
                val buffer = ByteArray(4096)
                var bytesRead: Int

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }

                outputStream.close()
                inputStream.close()

                file.absolutePath
            } catch (e: Exception) {
                Log.e("DownloadRingtoneTask", "Error downloading ringtone", e)
                null
            }
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String?) {
            loadingDialog.dismiss() // Ẩn loading dialog
            if (result != null) {
                val playRingtone = PlayRingtoneFragment.newInstance(result, ringtoneName)
                playRingtone.show(supportFragmentManager, playRingtone.tag)
            }
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000L) // Delay 1 giây
                setControlsEnabled(true)
            }
        }
    }

    @Suppress("DEPRECATION")
    @SuppressLint("StaticFieldLeak")
    private inner class DownloadAndSaveRingtoneTask(
        private val context: Context,
        private val ringtoneUrl: String,
        private val ringtoneName: String
    ) : AsyncTask<Void, Void, Boolean>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Boolean {
            return try {
                val url = URL(ringtoneUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    return false
                }
                Log.d("DownloadAndSaveRingtoneTask", "$ringtoneName.m4r")
                val file = File(context.filesDir, "$ringtoneName.m4r")
                if (file.exists()) {
                    file.delete()
                }
                val outputStream = FileOutputStream(file)
                val inputStream: InputStream = connection.inputStream
                val buffer = ByteArray(4096)
                var bytesRead: Int

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                val ringtoneUri = Uri.fromFile(file)
                saveRingtone(context, ringtoneUri.toString())
                outputStream.close()
                inputStream.close()

                true
            } catch (e: Exception) {
                Log.e("DownloadAndSaveRingtoneTask", "Error saving ringtone", e)
                false
            }
        }
    }
}
