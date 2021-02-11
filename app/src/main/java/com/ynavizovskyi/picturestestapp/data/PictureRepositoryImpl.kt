package com.ynavizovskyi.picturestestapp.data

import com.ynavizovskyi.picturestestapp.common.LOCAL
import com.ynavizovskyi.picturestestapp.common.REMOTE
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class PictureRepositoryImpl @Inject constructor(
    @Named(LOCAL) private val localStore: PictureDataStore,
    @Named(REMOTE) private val remoteStore: PictureDataStore
) : PictureRepository {

    override suspend fun loadPage(page: Int) {
        val pictures = remoteStore.loadPage(page)
        localStore.save(pictures)
    }

    override suspend fun observeNewPictures(): Flow<List<Picture>> {
        return localStore.observeNew().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun observeSeenPictures(): Flow<List<Picture>> {
        return localStore.observeSeen().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun markAsSeen(picture: Picture, isSeen: Boolean) {
        localStore.markAsSeen(picture.id, isSeen)
    }
}