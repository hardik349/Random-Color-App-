package com.example.colorapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {

    @Query("SELECT * FROM color_table")
    fun getAllColors(): Flow<List<ColorData>>

    @Upsert
    suspend fun insert(colorData: ColorData)

    @Delete
    suspend fun delete(colorData: ColorData)

    // Add an update function to update the 'synced' flag
    @Query("UPDATE color_table SET synced = :synced WHERE id = :id")
    suspend fun updateSynced(id: Int, synced: Boolean)
}