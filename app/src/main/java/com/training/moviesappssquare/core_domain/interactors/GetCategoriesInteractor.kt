package com.training.moviesappssquare.core_domain.interactors

import com.training.moviesappssquare.core_domain.repositories.CategoriesRepository
import javax.inject.Inject

class GetCategoriesInteractor @Inject constructor(
    private val categoriesRepository:CategoriesRepository
){
    suspend fun getCategoriesList(apiKey:String) = categoriesRepository.getCategoriesList(apiKey)
}