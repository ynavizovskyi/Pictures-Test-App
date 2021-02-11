package com.ynavizovskyi.picturestestapp.presetntation.base

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

abstract class BasePagerAdapter<T : PagerPage>(
    fragmentManager: FragmentManager,
    private val context: Context,
    private val pages: List<T>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = pages[position].createFragment()

    override fun getCount() = pages.size

    override fun getPageTitle(position: Int) = context.getString(pages[position].getPageTitleResId())
}