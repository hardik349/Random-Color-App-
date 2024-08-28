package com.example.colorapp.data

import com.example.colorapp.server.ColorDataFirebase
import com.example.colorapp.server.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.Random
import javax.inject.Inject

class ColorRepository @Inject constructor
    (
    private val colorDao: ColorDao,
    private val firebaseRepository: FirebaseRepository
) {

    fun getAllColors(): Flow<List<ColorData>> {
        return colorDao.getAllColors()

    }

    suspend fun addColor() {
        val randomColor = generateRandomColor()
        val colorData = ColorData(color = randomColor, date = System.currentTimeMillis())
        withContext(Dispatchers.IO) {
            colorDao.insert(colorData)
        }
    }

    private fun generateRandomColor(): String {
        val random = Random()
        return "#${String.format("%06x", random.nextInt(0xFFFFFF))}"
    }

    suspend fun syncColors() {

        val unSyncedColors = firebaseRepository.getUnSyncedColors(colorDao)
        unSyncedColors.forEach { colorData ->
            val colorDataFirebase =
                ColorDataFirebase(color = colorData.color, date = colorData.date)
            firebaseRepository.pushColorData(colorDataFirebase)

            withContext(Dispatchers.IO) {
                colorDao.updateSynced(colorData.id, true)
            }
        }
    }

     suspend fun getUnSyncedColors() : List<ColorData>{
        return colorDao.getAllColors().first().filter { !it.synced }
    }
}