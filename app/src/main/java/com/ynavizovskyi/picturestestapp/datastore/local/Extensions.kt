package com.ynavizovskyi.picturestestapp.datastore.local

import com.ynavizovskyi.picturestestapp.data.PictureData
import com.ynavizovskyi.picturestestapp.datastore.local.entity.PictureEntity

fun PictureData.toEntity() = PictureEntity(id, author, url)

fun PictureEntity.toData() = PictureData(id, author, url)