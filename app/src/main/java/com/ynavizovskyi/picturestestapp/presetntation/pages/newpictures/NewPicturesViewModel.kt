package com.ynavizovskyi.picturestestapp.presetntation.pages.newpictures

import androidx.lifecycle.viewModelScope
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.domain.usecase.LoadPicturesPageUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.MarkPictureAsSeenUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.ObservePicturesUseCase
import com.ynavizovskyi.picturestestapp.presetntation.BasePicturesViewModel
import com.ynavizovskyi.picturestestapp.presetntation.ListItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewPicturesViewModel @Inject constructor(
    observeUseCase: ObservePicturesUseCase,
    markPictureAsSeenUseCase: MarkPictureAsSeenUseCase,
    private val loadPageUseCase: LoadPicturesPageUseCase
) : BasePicturesViewModel(observeUseCase, markPictureAsSeenUseCase) {

    init {
        observePictures(false)
        loadPage(1)
    }

    private var lastLoadedPage = 0

    fun loadPage(page: Int){
        viewModelScope.launch {
            loadPageUseCase(page)
        }
    }

    override fun createViewState(pictures: List<Picture>): List<ListItem> {
        return super.createViewState(pictures) + ListItem.Loading(++lastLoadedPage)
    }


}