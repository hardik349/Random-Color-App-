package com.example.colorapp.modules

import android.app.Application
import androidx.room.Room
import com.example.colorapp.data.ColorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ColorDatabaseModule {

    @Singleton
    @Provides
    fun provideColorDatabase(application: Application) : ColorDatabase {
        return Room.databaseBuilder(
            application,
            ColorDatabase::class.java,
            "color_database"
        )
            .addMigrations(ColorDatabase.MIGRATION_1_2)
            .allowMainThreadQueries().build()
    }
}