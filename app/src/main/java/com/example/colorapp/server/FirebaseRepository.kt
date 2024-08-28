package com.example.colorapp.server

import com.example.colorapp.data.ColorDao
import com.example.colorapp.data.ColorData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    private val database : DatabaseReference = FirebaseDatabase.getInstance().reference
    private val colorRef = database.child("colors")

    suspend fun pushColorData(colorDataFirebase: ColorDataFirebase) {
        colorRef.push().setValue(colorDataFirebase).await()
    }

    suspend fun getUnSyncedColors(colorDao : ColorDao) : List<ColorData> {
        val allColors = colorDao.getAllColors().first()
        return allColors.filter { !it.synced }
    }


}