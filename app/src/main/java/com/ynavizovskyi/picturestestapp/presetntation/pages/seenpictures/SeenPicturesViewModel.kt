package com.ynavizovskyi.picturestestapp.presetntation.pages.seenpictures

import com.ynavizovskyi.picturestestapp.domain.usecase.MarkPictureAsSeenUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.ObservePicturesUseCase
import com.ynavizovskyi.picturestestapp.presetntation.BasePicturesViewModel
import javax.inject.Inject

class SeenPicturesViewModel @Inject constructor(
    observeUseCase: ObservePicturesUseCase,
    markPictureAsSeenUseCase: MarkPictureAsSeenUseCase
) : BasePicturesViewModel(observeUseCase, markPictureAsSeenUseCase) {

    init {
        observePictures(true)
    }

}