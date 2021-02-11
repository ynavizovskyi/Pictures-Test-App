package com.ynavizovskyi.picturestestapp.di

import com.ynavizovskyi.picturestestapp.presetntation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [FragmentBuilderModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindContainer(): MainActivity?

}