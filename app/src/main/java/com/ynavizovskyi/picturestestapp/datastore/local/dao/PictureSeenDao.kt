package com.ynavizovskyi.picturestestapp.datastore.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ynavizovskyi.picturestestapp.datastore.local.entity.PictureSeenEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PictureSeenDao : BaseRoomDao<PictureSeenEntity>() {

    @Query(" SELECT * FROM PictureSeenEntity")
    abstract fun observeById(): Flow<PictureSeenEntity>

}