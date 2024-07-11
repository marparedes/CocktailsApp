package com.marparedes.cocktailsapp.domain

import com.marparedes.cocktailsapp.common.Resource
import com.marparedes.cocktailsapp.common.Status
import com.marparedes.cocktailsapp.data.remote.dto.DrinksDTO
import com.marparedes.cocktailsapp.domain.repository.CocktailRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class CocktailRepositoryTest {

    @MockK
    private lateinit var repository: CocktailRepository

    @Before
    fun setUp() {
        repository = mockk()
    }

    @Test
    fun `test searchCocktail returns success`() = runTest {
        val cocktailName = "Margarita"
        val expectedDrinksDTO = DrinksDTO(
            drinks = emptyList()
        )
        val expectedResult = Resource.success(expectedDrinksDTO)

        coEvery { repository.searchCocktail(cocktailName) } returns expectedResult

        val actualResult = repository.searchCocktail(cocktailName)

        assertEquals(expectedResult.status, actualResult.status)
    }

    @Test
    fun `test searchCocktail returns error`() = runTest {
        val cocktailName = "Margarita"
        val expectedResult : Resource<Error> = Resource.error(Status.NETWORK_ERROR)

        val error: Response<Any> = Response.error(500, "".toResponseBody(null))

        coEvery { repository.searchCocktail(cocktailName) } throws HttpException(error)

        val actualResult = try {
            repository.searchCocktail(cocktailName)
        } catch (e: HttpException) {
            expectedResult
        }

        assertEquals(expectedResult, actualResult)
    }
}