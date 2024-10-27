package com.training.moviesappssquare.core_presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.moviesappssquare.core_data.remote.model.GenresItem
import com.training.moviesappssquare.core_data.remote.model.ResultsItem
import com.training.moviesappssquare.core_domain.interactors.GetCategoriesInteractor
import com.training.moviesappssquare.core_domain.interactors.GetMoviesByCategoryIdInteractor
import com.training.moviesappssquare.core_domain.interactors.GetPopularMoviesInteractor
import com.training.moviesappssquare.utils.APIConstants
import com.training.moviesappssquare.utils.MovieStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val popularMoviesInteractor: GetPopularMoviesInteractor,
    private val categoriesInteractor: GetCategoriesInteractor,
    private val moviesByCategoryIdInteractor: GetMoviesByCategoryIdInteractor
):ViewModel(){
    private var _popularMoviesFlow:MutableStateFlow<MovieStates<ResultsItem?>> = MutableStateFlow(MovieStates.UnSpecified)
    val popularMoviesFlow = _popularMoviesFlow.asStateFlow()

    private var _categoriesListFlow : MutableStateFlow<MovieStates<GenresItem?>> = MutableStateFlow(MovieStates.UnSpecified)
    val categoriesListFlow = _categoriesListFlow.asStateFlow()

    init {
        getPopularMovies(1)
        getCategoriesList()
    }

    fun getPopularMovies(page:Int){
        viewModelScope
            .launch {
                _popularMoviesFlow.emit(
                    popularMoviesInteractor.getPopularMovies(APIConstants.API_KEY,page)
                )
            }
    }

    fun getCategoriesList() = viewModelScope.launch {
        _categoriesListFlow.emit(
            categoriesInteractor.getCategoriesList(APIConstants.API_KEY)
        )
    }

    fun getMoviesByCategoryId(
        categoryId:Int,
        page:Int
    ){
        viewModelScope
            .launch {
               _popularMoviesFlow.emit(
                   moviesByCategoryIdInteractor.getMoviesByCategoryId(
                       APIConstants.API_KEY,
                       categoryId,
                       page
                   )
               )
            }
    }

    fun resetPopularMovies(){
        _popularMoviesFlow.value = MovieStates.UnSpecified
    }
}