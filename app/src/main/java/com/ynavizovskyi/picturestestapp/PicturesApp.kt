package com.ynavizovskyi.picturestestapp

import android.app.Activity
import android.app.Application
import com.ynavizovskyi.picturestestapp.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class PicturesApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
    }

    private fun initDependencyInjection(){
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

    }

}