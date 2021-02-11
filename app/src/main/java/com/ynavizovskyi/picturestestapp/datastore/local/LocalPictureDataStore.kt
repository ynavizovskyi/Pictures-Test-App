package com.ynavizovskyi.picturestestapp.datastore.local

import com.ynavizovskyi.picturestestapp.data.PictureData
import com.ynavizovskyi.picturestestapp.data.PictureDataStore
import com.ynavizovskyi.picturestestapp.datastore.local.dao.PictureDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPictureDataStore @Inject constructor(private val pictureDao: PictureDao): PictureDataStore {

    override suspend fun save(pictures: List<PictureData>) {
        pictureDao.insert(pictures.map { it.toEntity() })
    }

    override suspend fun loadPage(page: Int): List<PictureData> {
        TODO("Not yet implemented")
    }

    override suspend fun observeNew(): Flow<List<PictureData>> {
        return pictureDao.observerAll().map { list ->
            list.map { it.toData() }
        }
    }

    override suspend fun observeSeen(): Flow<List<PictureData>> {
        TODO("Not yet implemented")
    }
}