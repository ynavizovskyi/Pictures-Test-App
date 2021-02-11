package com.ynavizovskyi.picturestestapp.domain.usecase

import com.ynavizovskyi.picturestestapp.domain.dispatcher.DispatcherManager
import com.ynavizovskyi.picturestestapp.domain.repository.PictureRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadPicturesPageUseCase @Inject constructor(
    private val repository: PictureRepository,
    private val dispatcherManager: DispatcherManager
) {

    suspend operator fun invoke(page: Int){
        return withContext(dispatcherManager.io){
            repository.loadPage(page)
        }
    }

}