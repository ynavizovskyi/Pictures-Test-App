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
import kotlinx.coroutines.launch

abstract class BasePicturesViewModel(
    protected val observeUseCase: ObservePicturesUseCase,
    protected val loadPageUseCase: LoadPicturesPageUseCase,
    protected val markPictureAsSeenUseCase: MarkPictureAsSeenUseCase
) : ViewModel() {

    init {
        observePictures()
    }

    val undoMarkAsSeenLiveData: MutableLiveData<UndoDelete> = MutableLiveData()

    protected val itemCountDownMap = mutableMapOf<Picture, CountDownWrapper>()

    abstract protected fun observePictures()

    protected fun markPictureAsSeen(picture: Picture, isSeen: Boolean){
        viewModelScope.launch {
            markPictureAsSeenUseCase.invoke(picture, isSeen)
        }
    }

    protected open fun createViewState(pictures: List<Picture>): List<ListItem>{
        val result = pictures.map { picture ->
            val countDownValue = itemCountDownMap[picture]?.countDown?.count
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

