package com.training.moviesappssquare.core_domain.repositories

import com.training.moviesappssquare.core_data.remote.model.CategoriesResponse
import com.training.moviesappssquare.core_data.remote.model.GenresItem
import com.training.moviesappssquare.utils.MovieStates

interface CategoriesRepository {
    suspend fun getCategoriesList(
        apiKey:String
    ):MovieStates<GenresItem?>
}