package com.example.colorapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorapp.data.ColorRepository
import com.example.colorapp.server.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorViewModel @Inject constructor(
    private val repository: ColorRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    val colorList = repository.getAllColors()
    val unsyncedCount = mutableStateOf(0)

    fun addColor(){
        viewModelScope.launch {
            repository.addColor()
        }
    }

    fun syncColors(){
        viewModelScope.launch {
            repository.syncColors()
            unsyncedCount.value = repository.getUnSyncedColors().size
        }
    }
}