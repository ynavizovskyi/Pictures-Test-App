package com.ynavizovskyi.picturestestapp.datastore.remote

import com.ynavizovskyi.picturestestapp.data.DefaultPictureData
import com.ynavizovskyi.picturestestapp.datastore.remote.response.Picture

fun Picture.toData() = DefaultPictureData(id, author, download_url)