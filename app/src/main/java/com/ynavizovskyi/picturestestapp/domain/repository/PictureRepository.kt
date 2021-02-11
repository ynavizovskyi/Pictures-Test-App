package com.ynavizovskyi.picturestestapp.domain.repository

import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import kotlinx.coroutines.flow.Flow

interface PictureRepository {

    suspend fun loadPage(page: Int)

    suspend fun observeNewPictures(): Flow<List<Picture>>

    suspend fun observeSeenPictures(): Flow<List<Picture>>

}