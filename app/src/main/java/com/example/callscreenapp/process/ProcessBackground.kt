package com.example.callscreenapp.process

import com.example.callscreenapp.data.realm
import com.example.callscreenapp.database.BackgroundTheme
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File

fun ProcessBackground() {
    // Đường dẫn đến tệp JSON
    val jsonFile = File("")

    // Đọc nội dung của tệp JSON
    val bufferedReader = BufferedReader(jsonFile.reader())
    val jsonString = bufferedReader.use { it.readText() }

    // Chuyển đổi JSON thành mảng các đối tượng BackgroundTheme
    val gson = Gson()
    val themesArray = gson.fromJson(jsonString, Array<BackgroundTheme>::class.java)

}