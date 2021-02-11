package com.ynavizovskyi.picturestestapp.di

import android.content.Context
import com.ynavizovskyi.picturestestapp.datastore.local.DatabaseManager
import com.ynavizovskyi.picturestestapp.datastore.local.RoomDatabaseManager
import com.ynavizovskyi.picturestestapp.datastore.local.dao.PictureDao
import com.ynavizovskyi.picturestestapp.datastore.local.dao.PictureSeenDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Provides
    @Singleton
    fun providesDatabaseManager(
        context: Context,
    ): DatabaseManager {
        return RoomDatabaseManager.create(context)
    }

    @Provides
    @Singleton
    fun providesPictureDao(databaseManager: DatabaseManager): PictureDao {
        return databaseManager.pictureDao()
    }

    @Provides
    @Singleton
    fun providesPictureSeenDao(databaseManager: DatabaseManager): PictureSeenDao {
        return databaseManager.pictureSeenDao()
    }

}