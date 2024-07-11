package com.marparedes.cocktailsapp.domain

import app.cash.turbine.test
import com.marparedes.cocktailsapp.common.Resource
import com.marparedes.cocktailsapp.common.Status
import com.marparedes.cocktailsapp.data.remote.dto.DrinksDTO
import com.marparedes.cocktailsapp.domain.repository.CocktailRepository
import com.marparedes.cocktailsapp.domain.use_case.SearchCocktailByNameUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class SearchCocktailByNameUseCaseTest {

    @MockK
    private lateinit var repository: CocktailRepository

    private lateinit var searchCocktailByNameUseCase: SearchCocktailByNameUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        searchCocktailByNameUseCase = SearchCocktailByNameUseCase(repository)
    }

    @Test
    fun `when calling API should return success`() = runTest {
        val name = "something"
        val drinks = DrinksDTO(emptyList())
        val resource = Resource.success(drinks)

        coEvery { repository.searchCocktail(name.lowercase()) } returns resource

        searchCocktailByNameUseCase.invoke(name).test {
            assertEquals(Resource.loading(null), awaitItem())
            assertEquals(Status.SUCCESS, awaitItem().status)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should return network error on HttpException`() = runTest {
        val name = "cocktail"
        val error: Response<Any> = Response.error(500, "".toResponseBody(null))

        coEvery { repository.searchCocktail(name.lowercase()) } throws HttpException(error)

        searchCocktailByNameUseCase.invoke(name).test {
            assertEquals(Resource.loading(null), awaitItem())
            assertEquals(Status.NETWORK_ERROR, awaitItem().status)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should return error on IOException`() = runTest {
        val name = "Margarita"

        coEvery { repository.searchCocktail(name.lowercase()) } throws IOException()

        searchCocktailByNameUseCase.invoke(name).test {
            assertEquals(Resource.loading(null), awaitItem())
            assertEquals(Status.ERROR, awaitItem().status)
            awaitComplete()
        }
    }


}