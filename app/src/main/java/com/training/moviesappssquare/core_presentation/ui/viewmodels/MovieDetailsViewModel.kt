package com.training.moviesappssquare.core_presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.moviesappssquare.core_data.remote.model.MovieDetailsResponse
import com.training.moviesappssquare.core_domain.interactors.GetMovieDetailsInteractor
import com.training.moviesappssquare.utils.MovieDetailsStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsInteractor: GetMovieDetailsInteractor
):ViewModel(){

    private var _movieDetailsFlow : MutableStateFlow<MovieDetailsStates<MovieDetailsResponse?>> =
        MutableStateFlow(MovieDetailsStates.UnSpecified)
    val movieDetailsFlow = _movieDetailsFlow.asStateFlow()

    fun getMovieDetails(id:Int){
        viewModelScope.launch {
            _movieDetailsFlow.emit(
                movieDetailsInteractor.getMovieDetails(id)
            )
        }
    }

    fun resetMovieDetailsState(){
        viewModelScope.launch {
            _movieDetailsFlow.emit(MovieDetailsStates.UnSpecified)
        }
    }
}