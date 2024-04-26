package com.example.callscreenapp.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "DataImage")
data class DataImage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "urlBackground") val urlBackGround: String,
    @ColumnInfo(name = "urlIconCall") val urlIconCall: Int
)

@Dao
interface DataImageDao {
    @Insert
    fun insertAll(vararg data: DataImage)

    @Query("SELECT * FROM DataImage")
    fun getAll(): List<DataImage>

    @Delete
    fun delete(data: DataImage)

    @Update
    fun updateUsers(vararg data: DataImage)
}