package com.ynavizovskyi.picturestestapp.presetntation.pages.seenpictures

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.domain.usecase.LoadPicturesPageUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.MarkPictureAsSeenUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.ObservePicturesUseCase
import com.ynavizovskyi.picturestestapp.presetntation.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SeenPicturesViewModel @Inject constructor(
    observeUseCase: ObservePicturesUseCase,
    loadPageUseCase: LoadPicturesPageUseCase,
    markPictureAsSeenUseCase: MarkPictureAsSeenUseCase
) : BasePicturesViewModel(observeUseCase, loadPageUseCase, markPictureAsSeenUseCase) {

    val seenPicturesLiveData: MutableLiveData<List<ListItem>> = MutableLiveData()

    private var pictures = emptyList<Picture>()

    override fun observePictures() {
        viewModelScope.launch {
            observeUseCase.observeSeen().collect {
                pictures = it
                seenPicturesLiveData.value = createViewState(it)
            }
        }
    }

    fun startOrRestartMarkAsNewCountDown(picture: Picture){
        val ongoingCountDown = itemCountDownMap[picture]
        ongoingCountDown?.countDown?.cancel()

        val countDown = CountDown(viewModelScope, 3) {
            if (it == 0) {
                markPictureAsSeen(picture, false)
                undoMarkAsSeenLiveData.value =
                    UndoDelete(picture, itemCountDownMap[picture]?.clickNumber ?: 1) {
                        markPictureAsSeen(picture, true)
                    }
            } else {
                seenPicturesLiveData.value = createViewState(pictures)
            }
        }
        itemCountDownMap.put(picture,
            CountDownWrapper(countDown, ongoingCountDown?.clickNumber?.inc() ?: 1)
        )
        countDown.start()
    }

}