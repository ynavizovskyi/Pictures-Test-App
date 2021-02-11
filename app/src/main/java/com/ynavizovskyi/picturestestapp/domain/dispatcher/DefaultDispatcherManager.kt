package com.ynavizovskyi.picturestestapp.domain.dispatcher

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DefaultDispatcherManager @Inject constructor() : DispatcherManager {

    override val io: CoroutineContext = Dispatchers.IO
    override val ui: CoroutineContext = Dispatchers.Main

}