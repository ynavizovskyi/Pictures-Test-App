package com.ynavizovskyi.picturestestapp.presetntation.pages.newpictures

import androidx.lifecycle.viewModelScope
import com.ynavizovskyi.picturestestapp.common.PAGE_SIZE
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.domain.usecase.LoadPicturesPageUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.MarkPictureAsSeenUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.ObservePicturesUseCase
import com.ynavizovskyi.picturestestapp.presetntation.BasePicturesViewModel
import com.ynavizovskyi.picturestestapp.presetntation.ListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewPicturesViewModel @Inject constructor(
    observeUseCase: ObservePicturesUseCase,
    markPictureAsSeenUseCase: MarkPictureAsSeenUseCase,
    private val loadPageUseCase: LoadPicturesPageUseCase
) : BasePicturesViewModel(observeUseCase, markPictureAsSeenUseCase) {

    init {
        observePictures(false)
//        loadPage(1)
    }

    private var loadPageJob: Job? = null

    fun loadPage(page: Int){
        loadPageJob?.cancel()
        loadPageJob = viewModelScope.launch {
            loadPageUseCase(page)
        }
    }

    override fun createViewState(pictures: List<Picture>): List<ListItem> {
        val pictures =  super.createViewState(pictures)
        val nextPage = (pictures.size / PAGE_SIZE) + 1
        return pictures + ListItem.Loading(nextPage)
    }


}