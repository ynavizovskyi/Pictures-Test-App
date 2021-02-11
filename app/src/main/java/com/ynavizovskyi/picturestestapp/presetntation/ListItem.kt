package com.ynavizovskyi.picturestestapp.presetntation

import com.ynavizovskyi.picturestestapp.domain.entity.Picture

sealed class ListItem(val id: Long) {

    data class PictureItem(val picture: Picture, val countDownValue: CountDownValue?) : ListItem(picture.id)

    data class Loading(val page: Int) : ListItem(-1)

}

data class CountDownValue(val value: Int)