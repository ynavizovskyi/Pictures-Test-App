package com.ynavizovskyi.picturestestapp.di

import android.app.Application
import com.ynavizovskyi.picturestestapp.PicturesApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, AppModule::class, ActivityModule::class]
)
interface AppComponent {

    fun inject(application: PicturesApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}