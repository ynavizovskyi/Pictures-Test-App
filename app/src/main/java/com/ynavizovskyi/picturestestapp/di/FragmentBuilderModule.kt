package com.ynavizovskyi.picturestestapp.di

import com.ynavizovskyi.picturestestapp.presetntation.pages.SeenPicturesPageFragment
import com.ynavizovskyi.picturestestapp.presetntation.pages.UnseenPicturesPageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindSeenPicturesFragment(): SeenPicturesPageFragment?

    @ContributesAndroidInjector
    abstract fun bindUnseenPicturesFragment(): UnseenPicturesPageFragment?

}