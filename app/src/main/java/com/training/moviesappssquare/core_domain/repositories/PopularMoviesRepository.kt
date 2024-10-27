package com.training.moviesappssquare.core_domain.repositories

import com.training.moviesappssquare.core_data.remote.model.PopularMoviesResponse
import com.training.moviesappssquare.core_data.remote.model.ResultsItem
import com.training.moviesappssquare.utils.MovieStates

interface PopularMoviesRepository{
    suspend fun getPopularMovies(
        apiKey:String,
        page:Int
    ):MovieStates<ResultsItem?>
}