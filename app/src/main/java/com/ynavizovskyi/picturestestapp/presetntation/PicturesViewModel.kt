package com.ynavizovskyi.picturestestapp.presetntation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.domain.usecase.LoadPicturesPageUseCase
import com.ynavizovskyi.picturestestapp.domain.usecase.ObservePicturesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PicturesViewModel @Inject constructor(
    private val observeUseCase: ObservePicturesUseCase,
    private val loadPageUseCase: LoadPicturesPageUseCase
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
            observeUseCase.observeSeen().collect {
                seenPicturesLiveData.value = it
            }
        }
    }


    fun loadPage(page: Int){
        viewModelScope.launch {
            loadPageUseCase(page)
        }
    }

}