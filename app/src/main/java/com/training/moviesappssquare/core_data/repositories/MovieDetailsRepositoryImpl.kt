package com.training.moviesappssquare.core_data.repositories

import com.training.moviesappssquare.core_data.remote.model.MovieDetailsResponse
import com.training.moviesappssquare.core_data.remote.services.MovieServices
import com.training.moviesappssquare.core_domain.repositories.MovieDetailsRepository
import com.training.moviesappssquare.utils.MovieDetailsStates
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val movieServices: MovieServices
):MovieDetailsRepository{
    override suspend fun getMovieDetails(id: Int): MovieDetailsStates<MovieDetailsResponse?> =
        try {
            MovieDetailsStates.Loading
            val response = movieServices.getMovieDetails(id)
            if (response.isSuccessful){
                val result = response.body()
                if (result != null){
                    MovieDetailsStates.Successful(result)
                }else{
                    MovieDetailsStates.Error("Movie details not found")
                }
            }else{
                MovieDetailsStates.Error("Failed to fetch Movie details")
            }
        }catch (exception:Exception){
            MovieDetailsStates.Error("Exception occured ${exception.localizedMessage}")
        }
}