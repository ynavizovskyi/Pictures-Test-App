package com.ynavizovskyi.picturestestapp.data.decorator

import com.ynavizovskyi.picturestestapp.data.PictureData

data class PictureDataDecorator(private val picture: PictureData, private val seen: SeenData) :
    PictureData by picture, SeenData by seen