package com.training.moviesappssquare.core_domain.interactors

import com.training.moviesappssquare.core_domain.repositories.MovieDetailsRepository
import javax.inject.Inject

class GetMovieDetailsInteractor @Inject constructor(
    private val movieDetailsRepository:MovieDetailsRepository
){
    suspend fun getMovieDetails(id:Int) = movieDetailsRepository.getMovieDetails(id)
}