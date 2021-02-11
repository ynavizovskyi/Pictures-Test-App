package com.ynavizovskyi.picturestestapp.presetntation

import com.ynavizovskyi.picturestestapp.domain.entity.Picture

sealed class ListItem(val id: Long, val viewType: Int) {

    data class PictureItem(val picture: Picture, val countDownValue: Int?) : ListItem(picture.id, VIEW_TYPE_PICTURE)

    data class Loading(val nextPage: Int) : ListItem(-1, VIEW_TYPE_LOADING)

}

const val VIEW_TYPE_PICTURE = 1
const val VIEW_TYPE_LOADING = 2
