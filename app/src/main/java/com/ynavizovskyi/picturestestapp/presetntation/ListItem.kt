package com.ynavizovskyi.picturestestapp.presetntation

import com.ynavizovskyi.picturestestapp.domain.entity.Picture

sealed class ListItem {

    data class PictureItem(val picture: Picture, val countDownValue: CountDownValue?) : ListItem()

    data class Loading(val page: Int) : ListItem()

}

data class CountDownValue(val value: Int)