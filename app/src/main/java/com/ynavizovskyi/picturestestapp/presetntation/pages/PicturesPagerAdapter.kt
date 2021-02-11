package com.ynavizovskyi.picturestestapp.presetntation.pages

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.ynavizovskyi.picturestestapp.presetntation.PicturePage
import com.ynavizovskyi.picturestestapp.presetntation.base.BasePagerAdapter

internal class PicturesPagerAdapter(
    fragmentManager: FragmentManager,
    context: Context,
    pages: List<PicturePage>
) : BasePagerAdapter<PicturePage>(fragmentManager, context, pages)