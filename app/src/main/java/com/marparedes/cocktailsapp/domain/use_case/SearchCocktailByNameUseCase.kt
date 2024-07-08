package com.marparedes.cocktailsapp.domain.use_case

import com.marparedes.cocktailsapp.common.Resource
import com.marparedes.cocktailsapp.common.Status
import com.marparedes.cocktailsapp.domain.model.CocktailModel
import com.marparedes.cocktailsapp.domain.model.toDomain
import com.marparedes.cocktailsapp.domain.repository.CocktailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchCocktailByNameUseCase @Inject constructor(private val repository: CocktailRepository) {
    suspend fun invoke(name: String): Flow<Resource<List<CocktailModel>>> = flow {
        try {
            emit(Resource.loading(null))
            val data = repository.searchCocktail(name.lowercase()).data?.drinks?.map { it.toDomain() }
            emit(Resource.success(data))
        } catch(e: HttpException) {
            emit(Resource.error(Status.NETWORK_ERROR))
        } catch (e: IOException) {
            emit(Resource.error(Status.ERROR))
        }
    }
}