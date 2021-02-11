package com.ynavizovskyi.picturestestapp.datastore.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PictureEntity(
    @PrimaryKey val id: Long
)