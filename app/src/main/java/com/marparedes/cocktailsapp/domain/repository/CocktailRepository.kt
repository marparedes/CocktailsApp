package com.marparedes.cocktailsapp.domain.repository

import com.marparedes.cocktailsapp.data.remote.dto.DrinksDTO
import com.marparedes.cocktailsapp.common.Resource
import retrofit2.http.Query

interface CocktailRepository {
    suspend fun searchCocktail(@Query("s") cocktail: String): Resource<DrinksDTO>
}