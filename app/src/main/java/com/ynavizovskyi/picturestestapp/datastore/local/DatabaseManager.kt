package com.ynavizovskyi.picturestestapp.datastore.local

import com.ynavizovskyi.picturestestapp.datastore.local.dao.PictureDao

interface DatabaseManager {

    fun pictureDao(): PictureDao

}