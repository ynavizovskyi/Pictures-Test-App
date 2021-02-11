package com.ynavizovskyi.picturestestapp.data

import com.ynavizovskyi.picturestestapp.domain.entity.Picture

fun PictureData.toDomain() = Picture(id, author, url, width, height)

fun Picture.toData() = Picture(id, author, url, width, height)