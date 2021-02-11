package com.ynavizovskyi.picturestestapp.datastore.local.dao

import androidx.room.Dao
import com.ynavizovskyi.picturestestapp.datastore.local.entity.PictureEntity

@Dao
abstract class PictureDao : BaseRoomDao<PictureEntity>() {


}