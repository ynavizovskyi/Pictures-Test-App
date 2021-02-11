package com.ynavizovskyi.picturestestapp.datastore.local

import com.ynavizovskyi.picturestestapp.datastore.local.dao.PictureDao
import com.ynavizovskyi.picturestestapp.datastore.local.dao.PictureSeenDao

interface DatabaseManager {

    fun pictureDao(): PictureDao

    fun pictureSeenDao(): PictureSeenDao

}