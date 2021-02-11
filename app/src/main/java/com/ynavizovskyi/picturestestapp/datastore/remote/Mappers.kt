package com.ynavizovskyi.picturestestapp.datastore.remote

import com.ynavizovskyi.picturestestapp.data.PictureData
import com.ynavizovskyi.picturestestapp.datastore.remote.response.Picture

fun Picture.toData() = PictureData(id, author, url)