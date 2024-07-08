package com.marparedes.cocktailsapp.data.remote.api

import com.marparedes.cocktailsapp.data.remote.dto.DrinksDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {

    @GET("search.php")
    suspend fun searchCocktail(@Query("s") search: String): Response<DrinksDTO>
}