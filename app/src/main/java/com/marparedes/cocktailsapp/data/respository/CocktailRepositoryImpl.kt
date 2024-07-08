package com.marparedes.cocktailsapp.data.respository

import com.marparedes.cocktailsapp.data.remote.api.CocktailService
import com.marparedes.cocktailsapp.data.remote.api.apiCall
import com.marparedes.cocktailsapp.data.remote.dto.DrinksDTO
import com.marparedes.cocktailsapp.common.Resource
import com.marparedes.cocktailsapp.domain.repository.CocktailRepository
import javax.inject.Inject

class CocktailRepositoryImpl @Inject constructor(private val service: CocktailService): CocktailRepository {
    override suspend fun searchCocktail(cocktail: String): Resource<DrinksDTO> {
        return apiCall { service.searchCocktail(cocktail) }
    }

}