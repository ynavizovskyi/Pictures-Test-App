package com.ynavizovskyi.picturestestapp.presetntation.pages.seenpictures

import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.presetntation.BasePicturesViewModel
import com.ynavizovskyi.picturestestapp.presetntation.pages.BasePicturesPageFragment
import javax.inject.Inject

class SeenPicturesPageFragment : BasePicturesPageFragment() {

    @Inject
    lateinit var seenViewModel: SeenPicturesViewModel

    override val viewModel: BasePicturesViewModel
        get() = seenViewModel

    override val pictureItemClickListener: (Picture) -> Unit
        get() = { picture ->
            viewModel.startOrRestartMarkAsSeenCountDown(picture, false)
        }

    override val loadMoreListener: (Int) -> Unit
        get() = {}
}