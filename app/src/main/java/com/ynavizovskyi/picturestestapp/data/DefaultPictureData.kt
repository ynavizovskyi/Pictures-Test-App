package com.ynavizovskyi.picturestestapp.data

data class DefaultPictureData(
    override val id: Long,
    override val author: String,
    override val url: String,
    override val width: Int,
    override val height: Int
) : PictureData