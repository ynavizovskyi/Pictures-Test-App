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

    val undoDeleteLiveData: MutableLiveData<UndoDelete> = MutableLiveData()

    val newPicturesLiveData: MutableLiveData<List<ListItem>> = MutableLiveData()
    val seenPicturesLiveData: MutableLiveData<List<Picture>> = MutableLiveData()

    private val itemCountDownMap = mutableMapOf<Picture, CountDownWrapper>()

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
        val ongoingCountDown = itemCountDownMap[picture]
        ongoingCountDown?.countDown?.cancel()

        val countDown = CountDown(viewModelScope, 5){
            Log.v("dddd", itemCountDownMap[picture]?.clickNumber.toString())
            if(it == 0){
                markPictureAsSeen(picture, true)
                undoDeleteLiveData.value = UndoDelete(picture, itemCountDownMap[picture]?.clickNumber ?: 1){
                    markPictureAsSeen(picture, false)
                }
            } else {
                newPicturesLiveData.value = createViewState(pictures)
            }
        }

        itemCountDownMap.put(picture, CountDownWrapper(countDown, ongoingCountDown?.clickNumber?.inc() ?: 1))
        countDown.start()

    }

    private fun createViewState(pictures: List<Picture>): List<ListItem>{
        val result = pictures.map { picture ->
            val countDownValue = itemCountDownMap[picture]?.let {
                CountDownValue(it.countDown.count)
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

data class CountDownWrapper(val countDown: CountDown, val clickNumber: Int)

data class UndoDelete(val picture: Picture, val itemClickCount: Int, val undoAction: () -> Unit)

