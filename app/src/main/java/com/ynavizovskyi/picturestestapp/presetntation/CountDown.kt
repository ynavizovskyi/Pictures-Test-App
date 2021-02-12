package com.ynavizovskyi.picturestestapp.presetntation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch

class CountDown(private val scope: CoroutineScope, private var seconds: Int, private val tick: (seconds: Int) -> Unit){

    var count = seconds
        private set

    private val tickerChannel = ticker(1000, 0)

    fun start(){
        scope.launch {
            for (event in tickerChannel) {
                count = seconds
                tick.invoke(seconds)
                seconds--
                if(seconds < 0){
                    tickerChannel.cancel()
                }
            }
        }
    }

    fun cancel(){
        tickerChannel.cancel()
    }

}