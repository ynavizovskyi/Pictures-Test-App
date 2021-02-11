package com.ynavizovskyi.picturestestapp.domain.dispatcher

import kotlin.coroutines.CoroutineContext

interface DispatcherManager {

    val io: CoroutineContext
    val ui: CoroutineContext

}