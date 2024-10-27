package com.training.moviesappssquare.core_data.remote.services

import com.training.moviesappssquare.core_data.remote.model.CategoriesResponse
import com.training.moviesappssquare.core_data.remote.model.MovieDetailsResponse
import com.training.moviesappssquare.core_data.remote.model.PopularMoviesResponse
import com.training.moviesappssquare.core_data.remote.model.ResultsItem
import com.training.moviesappssquare.utils.APIConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServices{
    @GET(APIConstants.MOVIES_POPULAR)
    suspend fun getMoviesPopular(
        @Query(APIConstants.API_KEY_KEYWORD) apiKey: String ,
        @Query("page") page: Int
    ):Response<PopularMoviesResponse>

    @GET(APIConstants.CATEGORIES_LIST)
    suspend fun getCategoriesList(
        @Query(APIConstants.API_KEY_KEYWORD) apiKey:String
    ):Response<CategoriesResponse>

    @GET(APIConstants.DISCOVER_MOVIES_ENDPOINT)
    suspend fun getMoviesByCategoryId(
        @Query(APIConstants.API_KEY_KEYWORD) apiKey:String,
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1
    ):Response<PopularMoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id:Int,
        @Query("api_key") apiKey:String = APIConstants.API_KEY):Response<MovieDetailsResponse>
}