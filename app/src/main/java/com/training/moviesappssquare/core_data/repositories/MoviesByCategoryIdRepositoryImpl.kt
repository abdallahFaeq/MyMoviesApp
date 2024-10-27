package com.training.moviesappssquare.core_data.repositories

import com.training.moviesappssquare.core_data.remote.model.ResultsItem
import com.training.moviesappssquare.core_data.remote.services.MovieServices
import com.training.moviesappssquare.core_domain.repositories.MoviesByCategoryIdRepository
import com.training.moviesappssquare.utils.MovieStates
import javax.inject.Inject

class MoviesByCategoryIdRepositoryImpl @Inject constructor(
    private val movieServices: MovieServices
):MoviesByCategoryIdRepository{
    override suspend fun getMoviesByCategoryId(
        apiKey: String,
        categoryId: Int,
        page: Int
    ): MovieStates<ResultsItem?> = try {
        MovieStates.Loading
     val response = movieServices.getMoviesByCategoryId(apiKey, categoryId, page)
     if(response.isSuccessful){
         val results = response.body()?.results
         if (!results.isNullOrEmpty()){
             MovieStates.Successful(results)
         }else{
             MovieStates.Error("Movies not found")
         }
     }else{
         MovieStates.Error("Failed to fetch movies by id")
     }
    }catch (exception:Exception){
        MovieStates.Error("Exception occured ${exception.localizedMessage}")
    }
}