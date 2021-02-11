package com.ynavizovskyi.picturestestapp.presetntation.base

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

interface PagerPage {

    fun createFragment(): Fragment

    @StringRes
    fun getPageTitleResId(): Int

}