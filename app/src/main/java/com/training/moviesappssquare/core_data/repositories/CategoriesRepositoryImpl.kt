package com.training.moviesappssquare.core_data.repositories

import androidx.compose.ui.res.stringResource
import com.training.moviesappssquare.core_data.remote.model.CategoriesResponse
import com.training.moviesappssquare.core_data.remote.model.GenresItem
import com.training.moviesappssquare.core_data.remote.services.MovieServices
import com.training.moviesappssquare.core_domain.repositories.CategoriesRepository
import com.training.moviesappssquare.utils.MovieStates
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val movieServices: MovieServices
):CategoriesRepository{
    override suspend fun getCategoriesList(apiKey: String): MovieStates<GenresItem?> =
        try {
            val response = movieServices.getCategoriesList(apiKey)
            if (response.isSuccessful){
                val results = response.body()?.genres
                if(!results.isNullOrEmpty()){
                    MovieStates.Successful(results)
                }else{
                    MovieStates.Error("Categories not found")
                }
            }else{
                MovieStates.Error("Failed to fetch categories")
            }
        }catch (exception:Exception){
            MovieStates.Error("Exception occured ${exception.localizedMessage}")
        }
}