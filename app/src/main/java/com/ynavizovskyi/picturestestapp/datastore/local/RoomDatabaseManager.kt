package com.ynavizovskyi.picturestestapp.datastore.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ynavizovskyi.picturestestapp.datastore.local.entity.PictureEntity

@Database(entities = [PictureEntity::class], version = 1)
abstract class RoomDatabaseManager : RoomDatabase(), DatabaseManager {

    companion object {
        fun create(context: Context): DatabaseManager {
            return Room
                .databaseBuilder(context, RoomDatabaseManager::class.java, "contacts")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}