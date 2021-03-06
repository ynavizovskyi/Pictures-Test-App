package com.ynavizovskyi.picturestestapp.presetntation

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.ynavizovskyi.picturestestapp.R
import com.ynavizovskyi.picturestestapp.presetntation.base.PagerPage
import com.ynavizovskyi.picturestestapp.presetntation.pages.newpictures.NewPicturesPageFragment
import com.ynavizovskyi.picturestestapp.presetntation.pages.seenpictures.SeenPicturesPageFragment

sealed class PicturePage(val initializer: () -> Fragment, @StringRes val titleStringId: Int): PagerPage {

    override fun createFragment() = initializer.invoke()

    override fun getPageTitleResId() = titleStringId
}

class SeenPicturesPage :
    PicturePage({ SeenPicturesPageFragment() }, R.string.page_title_seen)

class NewPicturesPage() : PicturePage({ NewPicturesPageFragment() }, R.string.page_title_new)
