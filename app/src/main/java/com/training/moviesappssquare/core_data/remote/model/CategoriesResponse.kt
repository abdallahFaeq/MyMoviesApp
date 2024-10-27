package com.training.moviesappssquare.core_data.remote.model

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
