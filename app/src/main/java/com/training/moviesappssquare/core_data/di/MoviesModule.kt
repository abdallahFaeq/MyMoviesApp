package com.training.moviesappssquare.core_data.di

import com.training.moviesappssquare.core_data.remote.services.MovieServices
import com.training.moviesappssquare.core_data.repositories.CategoriesRepositoryImpl
import com.training.moviesappssquare.core_data.repositories.MovieDetailsRepositoryImpl
import com.training.moviesappssquare.core_data.repositories.MoviesByCategoryIdRepositoryImpl
import com.training.moviesappssquare.core_data.repositories.PopularMoviesRepositoryImpl
import com.training.moviesappssquare.core_domain.repositories.CategoriesRepository
import com.training.moviesappssquare.core_domain.repositories.MovieDetailsRepository
import com.training.moviesappssquare.core_domain.repositories.MoviesByCategoryIdRepository
import com.training.moviesappssquare.core_domain.repositories.PopularMoviesRepository
import com.training.moviesappssquare.utils.APIConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoviesModule {

    @Provides
    @Singleton
    fun provideMoviesAPI():Retrofit{
        return Retrofit.Builder()
            .baseUrl(APIConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideServices(retrofit:Retrofit): MovieServices {
        return retrofit.create(MovieServices::class.java)
    }

    @Provides
    fun providePopularMoviesRepository(
        movieServices: MovieServices
    ):PopularMoviesRepository{
        return PopularMoviesRepositoryImpl(movieServices)
    }

    @Provides
    fun provideCategoriesRepository(
        movieServices: MovieServices
    ):CategoriesRepository{
        return CategoriesRepositoryImpl(movieServices)
    }

    @Provides
    fun provideMoviesByCategoryId(
        movieServices: MovieServices
    ):MoviesByCategoryIdRepository = MoviesByCategoryIdRepositoryImpl(movieServices)

    @Provides
    fun provideMovieDetailsRepository(
        movieServices: MovieServices
    ):MovieDetailsRepository = MovieDetailsRepositoryImpl(movieServices)
}