package com.ynavizovskyi.picturestestapp.presetntation.pages.newpictures

import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.presetntation.BasePicturesViewModel
import com.ynavizovskyi.picturestestapp.presetntation.pages.BasePicturesPageFragment
import javax.inject.Inject

class NewPicturesPageFragment : BasePicturesPageFragment() {

    @Inject
    lateinit var newViewModel: NewPicturesViewModel

    override val viewModel: BasePicturesViewModel
        get() = newViewModel

    override val pictureItemClickListener: (Picture) -> Unit
        get() = { picture ->
            viewModel.startOrRestartMarkAsSeenCountDown(picture, true)
        }

    override val loadMoreListener: (Int) -> Unit
        get() = { nextPage ->
            newViewModel.loadPage(nextPage)
        }

}