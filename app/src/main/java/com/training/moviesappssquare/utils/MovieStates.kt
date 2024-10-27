package com.training.moviesappssquare.utils

sealed class MovieStates <out T>{
    data class Successful<T>(val data:List<T>): MovieStates<T>()
    data class Error(val error:String) : MovieStates<Nothing>()
    object Loading:MovieStates<Nothing>()
    object UnSpecified : MovieStates<Nothing>()
}


sealed class MovieDetailsStates<out T>{
    data class Successful<T>(val data:T): MovieDetailsStates<T>()
    data class Error(val error:String) : MovieDetailsStates<Nothing>()
    object Loading:MovieDetailsStates<Nothing>()
    object UnSpecified : MovieDetailsStates<Nothing>()
}