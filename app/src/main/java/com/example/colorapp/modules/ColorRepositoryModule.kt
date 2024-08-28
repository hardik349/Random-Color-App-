package com.example.colorapp.modules

import com.example.colorapp.data.ColorDao
import com.example.colorapp.data.ColorRepository
import com.example.colorapp.server.FirebaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ColorRepositoryModule {

    @Singleton
    @Provides
    fun provideColorRepository(colorDao: ColorDao, firebaseRepository: FirebaseRepository ): ColorRepository {
        return ColorRepository(colorDao, firebaseRepository)
    }
}