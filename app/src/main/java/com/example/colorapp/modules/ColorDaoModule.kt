package com.example.colorapp.modules

import com.example.colorapp.data.ColorDao
import com.example.colorapp.data.ColorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ColorDaoModule {

    @Singleton
    @Provides
    fun provideColorDao(colorDatabase: ColorDatabase): ColorDao {
        return colorDatabase.colorDao()
    }
}
