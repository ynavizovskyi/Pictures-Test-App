package com.ynavizovskyi.picturestestapp.domain.usecase

import com.ynavizovskyi.picturestestapp.domain.dispatcher.DispatcherManager
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ObservePicturesUseCase @Inject constructor(
    private val repository: PictureRepository,
    private val dispatcherManager: DispatcherManager
) {

    suspend fun observe(observeSeen: Boolean): Flow<List<Picture>> {
        return withContext(dispatcherManager.io){
            if(observeSeen){
                repository.observeSeenPictures()
            } else {
                repository.observeNewPictures()
            }
        }
    }
//    suspend fun observeNew(): Flow<List<Picture>> {
//        return withContext(dispatcherManager.io){
//            repository.observeNewPictures()
//        }
//    }

    suspend fun observeSeen(): Flow<List<Picture>> {
        return withContext(dispatcherManager.io){
            repository.observeSeenPictures()
        }
    }

}