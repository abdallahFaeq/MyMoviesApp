package com.training.moviesappssquare.core_domain.repositories

import com.training.moviesappssquare.core_data.remote.model.PopularMoviesResponse
import com.training.moviesappssquare.core_data.remote.model.ResultsItem
import com.training.moviesappssquare.utils.MovieStates

interface MoviesByCategoryIdRepository {
    suspend fun getMoviesByCategoryId(
        apiKey:String,
        categoryId:Int,
        page:Int = 1
    ):MovieStates<ResultsItem?>
}