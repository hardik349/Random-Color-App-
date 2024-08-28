package com.example.colorapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ColorData :: class], version = 2)
@TypeConverters(DateConverter :: class)
abstract class ColorDatabase : RoomDatabase() {
    abstract fun colorDao() : ColorDao

    companion object{
        val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS color_table (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "color_name TEXT NOT NULL," +
                            "date INTEGER NOT NULL," +
                            "synced INTEGER DEFAULT 0" +
                            ")"
                )

                database.execSQL("ALTER TABLE color_table ADD COLUMN synced INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}