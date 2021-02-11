package com.ynavizovskyi.picturestestapp.data

import com.ynavizovskyi.picturestestapp.domain.entity.Picture

fun PictureData.toDomain() = Picture(id, author, url)

fun Picture.toData() = Picture(id, author, url)