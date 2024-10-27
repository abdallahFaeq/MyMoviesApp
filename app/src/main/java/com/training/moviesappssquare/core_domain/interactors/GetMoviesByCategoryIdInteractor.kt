package com.training.moviesappssquare.core_domain.interactors

import com.training.moviesappssquare.core_domain.repositories.MoviesByCategoryIdRepository
import javax.inject.Inject

class GetMoviesByCategoryIdInteractor @Inject constructor(
    private val moviesByCategoryIdRepository:MoviesByCategoryIdRepository
){
    suspend fun getMoviesByCategoryId(
        apiKey:String,
        categoryId:Int,
        page:Int
    ) = moviesByCategoryIdRepository.getMoviesByCategoryId(apiKey,categoryId,page)

}