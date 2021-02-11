package com.ynavizovskyi.picturestestapp.datastore.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ynavizovskyi.picturestestapp.datastore.local.entity.PictureEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PictureDao : BaseRoomDao<PictureEntity>() {

    @Query(" SELECT * FROM PictureEntity")
    abstract fun observerAll(): Flow<List<PictureEntity>>


}