package com.ynavizovskyi.picturestestapp.datastore.local

import com.ynavizovskyi.picturestestapp.data.DefaultPictureData
import com.ynavizovskyi.picturestestapp.data.PictureData
import com.ynavizovskyi.picturestestapp.datastore.local.entity.PictureEntity

fun PictureData.toEntity() = PictureEntity(id, author, url, width, height)

fun PictureEntity.toData() = DefaultPictureData(id, author, url, width, height)