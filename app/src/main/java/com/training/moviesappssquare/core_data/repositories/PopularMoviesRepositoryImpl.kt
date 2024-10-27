package com.training.moviesappssquare.core_data.repositories

import com.training.moviesappssquare.core_data.remote.model.ResultsItem
import com.training.moviesappssquare.core_data.remote.services.MovieServices
import com.training.moviesappssquare.core_domain.repositories.PopularMoviesRepository
import com.training.moviesappssquare.utils.MovieStates
import javax.inject.Inject

class PopularMoviesRepositoryImpl @Inject constructor(
    private val moviesServices: MovieServices
): PopularMoviesRepository{
    override suspend fun getPopularMovies(
        apiKey:String,
        page: Int): MovieStates<ResultsItem?> {
        return try {
            MovieStates.Loading
            val response = moviesServices.getMoviesPopular(apiKey,page)
            if (response.isSuccessful){
                val results = response.body()?.results
                if (!results.isNullOrEmpty()){
                    MovieStates.Successful(results)
                }else{
                    MovieStates.Error("Movies not found")
                }
            }else{
                MovieStates.Error("Failed to fetch movies")
            }
        }catch (exception:Exception){
            MovieStates.Error("Exception occured ${exception.localizedMessage}")
        }
    }
    }