package com.ynavizovskyi.picturestestapp.datastore.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PictureSeenEntity(@PrimaryKey val pictureId: Long, val isSeen: Boolean)