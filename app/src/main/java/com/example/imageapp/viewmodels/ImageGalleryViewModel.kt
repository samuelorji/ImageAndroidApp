package com.example.imageapp.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.imageapp.repositories.ImageRepository

class ImageGalleryViewModel @ViewModelInject constructor(
    private val unsplashImageRepo: ImageRepository,
    @Assisted state : SavedStateHandle ) : ViewModel() {

    val imageQuery = state.getLiveData<String>(CURRENT_QUERY)
    val images = imageQuery.switchMap { query ->
        unsplashImageRepo.searchFor(query).cachedIn(viewModelScope)
    }

    fun searchFor(query: String) {
        imageQuery.value = query
    }

    companion object {
        private val CURRENT_QUERY = ""
    }

}