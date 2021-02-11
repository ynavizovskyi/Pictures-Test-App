package com.ynavizovskyi.picturestestapp.presetntation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.domain.usecase.LoadPicturesPageUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.MarkPictureAsSeenUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.ObservePicturesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PicturesViewModel @Inject constructor(
    private val observeUseCase: ObservePicturesUseCase,
    private val loadPageUseCase: LoadPicturesPageUseCase,
    private val markPictureAsSeenUseCase: MarkPictureAsSeenUseCase
) : ViewModel() {

    init {
        loadPage(1)
        observePictures()
    }

    val newPicturesLiveData: MutableLiveData<List<ListItem>> = MutableLiveData()
    val seenPicturesLiveData: MutableLiveData<List<Picture>> = MutableLiveData()

    private val itemCountDownMap = mutableMapOf<Picture, CountDown>()

    private var pictures = emptyList<Picture>()

    private fun observePictures(){
        viewModelScope.launch {
            observeUseCase.observeNew().collect {
                pictures = it
                newPicturesLiveData.value = createViewState(it)
            }
        }
        viewModelScope.launch {
            observeUseCase.observeSeen().collect {
                seenPicturesLiveData.value = it
            }
        }
    }


    private fun loadPage(page: Int){
        viewModelScope.launch {
            loadPageUseCase(page)
        }
    }

    fun markPictureAsSeen(picture: Picture, isSeen: Boolean){
        viewModelScope.launch {
            markPictureAsSeenUseCase.invoke(picture, isSeen)
        }
    }

    fun startOrRestartDeleteCountDown(picture: Picture){
        itemCountDownMap[picture]?.cancel()

        val countDown = CountDown(viewModelScope, 10){
            Log.v("dddd", it.toString())
            newPicturesLiveData.value = createViewState(pictures)
        }

        itemCountDownMap.put(picture, countDown)
        countDown.start()

    }

    private fun createViewState(pictures: List<Picture>): List<ListItem>{
        val result = pictures.map { picture ->
            val countDownValue = itemCountDownMap[picture]?.let {
                CountDownValue(it.count)
            }
            ListItem.PictureItem(picture, countDownValue)
        }
        return result
    }

}

class CountDown(private val scope: CoroutineScope, private var seconds: Int, private val tick: (seconds: Int) -> Unit){

    var count = seconds
    private set

    private val tickerChannel = ticker(1000, 0)

    fun start(){
        scope.launch {
            for (event in tickerChannel) {
                tick.invoke(seconds)
                count = seconds
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