package com.ynavizovskyi.picturestestapp.di

import android.app.Application
import android.content.Context
import com.ynavizovskyi.picturestestapp.common.LOCAL
import com.ynavizovskyi.picturestestapp.common.REMOTE
import com.ynavizovskyi.picturestestapp.data.PictureDataStore
import com.ynavizovskyi.picturestestapp.data.PictureRepositoryImpl
import com.ynavizovskyi.picturestestapp.datastore.local.LocalPictureDataStore
import com.ynavizovskyi.picturestestapp.datastore.remote.RemotePictureDataStore
import com.ynavizovskyi.picturestestapp.domain.dispatcher.DefaultDispatcherManager
import com.ynavizovskyi.picturestestapp.domain.dispatcher.DispatcherManager
import com.ynavizovskyi.picturestestapp.domain.repository.PictureRepository
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module(includes = [NetworkModule::class, LocalModule::class])
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun bindDispatcherManager(mana: DefaultDispatcherManager): DispatcherManager

    @Binds
    abstract fun bindRepository(repository: PictureRepositoryImpl): PictureRepository

    @Binds
    @Named(REMOTE)
    abstract fun bindRemoteContactsDataStore(dataStore: RemotePictureDataStore): PictureDataStore

    @Binds
    @Named(LOCAL)
    abstract fun bindLocalContactsDataStore(dataStore: LocalPictureDataStore): PictureDataStore


}