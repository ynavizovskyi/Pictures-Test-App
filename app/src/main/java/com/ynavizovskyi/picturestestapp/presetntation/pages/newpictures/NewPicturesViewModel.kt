package com.ynavizovskyi.picturestestapp.presetntation.pages.newpictures

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

class NewPicturesViewModel @Inject constructor(
    observeUseCase: ObservePicturesUseCase,
    loadPageUseCase: LoadPicturesPageUseCase,
    markPictureAsSeenUseCase: MarkPictureAsSeenUseCase
) : BasePicturesViewModel(observeUseCase, loadPageUseCase, markPictureAsSeenUseCase) {

//    init {
//        loadPage(1)
//    }

    val newPicturesLiveData: MutableLiveData<List<ListItem>> = MutableLiveData()

    private var pictures = emptyList<Picture>()
    private var lastLoadedPage = 0

    fun loadPage(page: Int){
        viewModelScope.launch {
            loadPageUseCase(page)
        }
    }

    override fun observePictures() {
        viewModelScope.launch {
            observeUseCase.observeNew().collect {
                pictures = it
                newPicturesLiveData.value = createViewState(it)
            }
        }
    }

    override fun createViewState(pictures: List<Picture>): List<ListItem> {
        return super.createViewState(pictures) + ListItem.Loading(++lastLoadedPage)
    }

    fun startOrRestartMarkAsSeenCountDown(picture: Picture){
        val ongoingCountDown = itemCountDownMap[picture]
        ongoingCountDown?.countDown?.cancel()

        val countDown = CountDown(viewModelScope, 3) {
            if (it == 0) {
                markPictureAsSeen(picture, true)
                undoMarkAsSeenLiveData.value =
                    UndoDelete(picture, itemCountDownMap[picture]?.clickNumber ?: 1) {
                        markPictureAsSeen(picture, false)
                    }
            } else {
                newPicturesLiveData.value = createViewState(pictures)
            }
        }
        itemCountDownMap.put(picture,
            CountDownWrapper(countDown, ongoingCountDown?.clickNumber?.inc() ?: 1)
        )
        countDown.start()
    }


}