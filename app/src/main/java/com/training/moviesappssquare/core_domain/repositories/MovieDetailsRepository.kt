package com.training.moviesappssquare.core_domain.repositories

import com.training.moviesappssquare.core_data.remote.model.MovieDetailsResponse
import com.training.moviesappssquare.utils.MovieDetailsStates

interface MovieDetailsRepository {
    suspend fun getMovieDetails(
        id:Int
    ): MovieDetailsStates<MovieDetailsResponse?>
}