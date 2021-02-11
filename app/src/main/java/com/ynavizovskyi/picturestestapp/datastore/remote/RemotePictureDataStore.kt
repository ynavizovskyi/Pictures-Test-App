package com.ynavizovskyi.picturestestapp.datastore.remote

import com.ynavizovskyi.picturestestapp.data.PictureData
import com.ynavizovskyi.picturestestapp.data.PictureDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemotePictureDataStore @Inject constructor(private val service: PicturesService) : PictureDataStore {

    override suspend fun save(pictures: List<PictureData>) {
        TODO("Not yet implemented")
    }

    override suspend fun loadPage(page: Int): List<PictureData> {
        return service.getContacts(page).map { it.toData() }
    }

    override suspend fun observeNew(): Flow<List<PictureData>> {
        TODO("Not yet implemented")
    }

    override suspend fun observeSeen(): Flow<List<PictureData>> {
        TODO("Not yet implemented")
    }
}