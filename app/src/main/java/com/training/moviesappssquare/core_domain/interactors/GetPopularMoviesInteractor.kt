package com.training.moviesappssquare.core_domain.interactors

import com.training.moviesappssquare.core_data.remote.model.ResultsItem
import com.training.moviesappssquare.core_domain.repositories.PopularMoviesRepository
import com.training.moviesappssquare.utils.MovieStates
import javax.inject.Inject

class GetPopularMoviesInteractor @Inject constructor(
    private val moviesRepository:PopularMoviesRepository
){
    suspend fun getPopularMovies(
        apiKey:String,
        page:Int):MovieStates<ResultsItem?>{
        return moviesRepository.getPopularMovies(apiKey,page)
    }
}