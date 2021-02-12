package com.ynavizovskyi.picturestestapp.data

import com.ynavizovskyi.picturestestapp.data.decorator.SeenData
import com.ynavizovskyi.picturestestapp.domain.entity.Picture

fun PictureData.toDomain(): Picture {
    val isSeen = (this as? SeenData)?.pictureSeen ?: false
    return Picture(id, author, url, width, height, isSeen)
}

fun Picture.toData() = DefaultPictureData(id, author, url, width, height)