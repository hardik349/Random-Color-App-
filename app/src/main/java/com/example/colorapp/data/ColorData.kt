package com.example.colorapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color_table")
data class ColorData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "color_name")
    val color: String,
    @ColumnInfo(name = "date")
    val date: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "synced")
    var synced: Boolean = false // Add a 'synced' flag
)
