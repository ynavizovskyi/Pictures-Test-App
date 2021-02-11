package com.ynavizovskyi.picturestestapp.domain.usecase

import com.ynavizovskyi.picturestestapp.domain.dispatcher.DispatcherManager
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.domain.repository.PictureRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarkPictureAsSeenUseCase @Inject constructor(
    private val repository: PictureRepository,
    private val dispatcherManager: DispatcherManager
) {

    suspend operator fun invoke(picture: Picture, isSeen: Boolean){
        return withContext(dispatcherManager.io){
            repository.markAsSeen(picture, isSeen)
        }
    }

}