package com.ynavizovskyi.picturestestapp.presetntation

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

abstract class BasePicturesViewModel(
    private val observeUseCase: ObservePicturesUseCase,
    private val markPictureAsSeenUseCase: MarkPictureAsSeenUseCase
) : ViewModel() {


    val picturesLiveData: MutableLiveData<List<ListItem>> = MutableLiveData()
    val undoMarkPictureLiveData: MutableLiveData<UndoDelete> = MutableLiveData()

    private var pictures = emptyList<Picture>()
    private val itemCountDownMap = mutableMapOf<Picture, CountDownWrapper>()


    protected open fun createViewState(pictures: List<Picture>): List<ListItem>{
        val result = pictures.map { picture ->
            val countDownValue = itemCountDownMap[picture]?.countDown?.count?.let {
                if(it > 0) it else null
            }
            ListItem.PictureItem(picture, countDownValue)
        }
        return result
    }

    fun observePictures(observeSeen: Boolean) {
        viewModelScope.launch {
            observeUseCase.observe(observeSeen).collect {
                pictures = it
                picturesLiveData.value = createViewState(it)
            }
        }
    }


    fun startOrRestartMarkAsSeenCountDown(picture: Picture, markAsSeen: Boolean){
        val ongoingCountDown = itemCountDownMap[picture]
        ongoingCountDown?.countDown?.cancel()

        val countDown = createCountDown(picture, markAsSeen)
        val countDownWrapper = CountDownWrapper(countDown, ongoingCountDown?.clickNumber?.inc() ?: 1)

        itemCountDownMap[picture] = countDownWrapper
        countDown.start()
    }

    private fun markPictureAsSeen(picture: Picture, isSeen: Boolean){
        viewModelScope.launch {
            markPictureAsSeenUseCase.invoke(picture, isSeen)
        }
    }

    private fun createCountDown(picture: Picture, markAsSeen: Boolean): CountDown{
        return CountDown(viewModelScope, 3) {
            if (it == 0) {
                markPictureAsSeen(picture, markAsSeen)
                undoMarkPictureLiveData.value =
                    UndoDelete(picture, itemCountDownMap[picture]?.clickNumber ?: 1) {
                        markPictureAsSeen(picture, !markAsSeen)
                    }
            } else {
                picturesLiveData.value = createViewState(pictures)
            }
        }
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

