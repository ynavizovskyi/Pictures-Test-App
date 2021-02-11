package com.ynavizovskyi.picturestestapp.presetntation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.domain.usecase.LoadPicturesPageUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.MarkPictureAsSeenUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.ObservePicturesUseCase
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

    val newPicturesLiveData: MutableLiveData<List<Picture>> = MutableLiveData()
    val seenPicturesLiveData: MutableLiveData<List<Picture>> = MutableLiveData()

    private fun observePictures(){
        viewModelScope.launch {
            observeUseCase.observeNew().collect {
                newPicturesLiveData.value = it
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

}