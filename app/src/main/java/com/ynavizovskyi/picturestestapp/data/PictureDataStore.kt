package com.ynavizovskyi.picturestestapp.data

import kotlinx.coroutines.flow.Flow

interface PictureDataStore {

    suspend fun save(pictures: List<PictureData>)

    suspend fun loadPage(page: Int): List<PictureData>

    suspend fun observeNew(): Flow<List<PictureData>>

    suspend fun observeSeen(): Flow<List<PictureData>>

}