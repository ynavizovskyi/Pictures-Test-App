package com.ynavizovskyi.picturestestapp.datastore.local

import com.ynavizovskyi.picturestestapp.data.PictureData
import com.ynavizovskyi.picturestestapp.data.PictureDataStore
import com.ynavizovskyi.picturestestapp.datastore.local.dao.PictureDao
import com.ynavizovskyi.picturestestapp.datastore.local.dao.PictureSeenDao
import com.ynavizovskyi.picturestestapp.datastore.local.entity.PictureSeenEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPictureDataStore @Inject constructor(
    private val pictureDao: PictureDao,
    private val pictureSeenDao: PictureSeenDao
) : PictureDataStore {

    override suspend fun save(pictures: List<PictureData>) {
        pictureSeenDao.insert(pictures.map { PictureSeenEntity(it.id, false) })
        pictureDao.insert(pictures.map { it.toEntity() })
    }

    override suspend fun markAsSeen(pictureId: Long, isSeen: Boolean) {
        pictureSeenDao.insert(PictureSeenEntity(pictureId, isSeen))
    }

    override suspend fun loadPage(page: Int): List<PictureData> {
        TODO("Not yet implemented")
    }

    override suspend fun observeNew(): Flow<List<PictureData>> {
        return pictureDao.observerSeen(false).map { list ->
            list.map { it.toData() }
        }
    }

    override suspend fun observeSeen(): Flow<List<PictureData>> {
        return pictureDao.observerSeen(true).map { list ->
            list.map { it.toData() }
        }
    }
}