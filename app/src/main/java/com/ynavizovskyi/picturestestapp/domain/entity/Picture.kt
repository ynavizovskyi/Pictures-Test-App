package com.ynavizovskyi.picturestestapp.domain.entity

data class Picture(
    val id: Long,
    val author: String,
    val url: String,
    val width: Int,
    val height: Int,
    val isSeen: Boolean
)

fun Picture.aspectRatio(): Float = width.toFloat() / height.toFloat()