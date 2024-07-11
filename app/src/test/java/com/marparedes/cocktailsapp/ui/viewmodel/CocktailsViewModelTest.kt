package com.marparedes.cocktailsapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marparedes.cocktailsapp.MainDispatcherRule
import com.marparedes.cocktailsapp.common.Resource
import com.marparedes.cocktailsapp.common.Status
import com.marparedes.cocktailsapp.domain.model.CocktailModel
import com.marparedes.cocktailsapp.domain.use_case.SearchCocktailByNameUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CocktailsViewModelTest {

    @MockK
    private lateinit var searchCocktailByNameUseCase: SearchCocktailByNameUseCase

    private lateinit var viewModel: CocktailsViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { searchCocktailByNameUseCase.invoke("") } returns flow {
            emit(Resource.success(emptyList()))
        }
        viewModel = CocktailsViewModel(searchCocktailByNameUseCase)
    }

    @Test
    fun `searchCocktail returns success with data`() = runTest {
        val cocktailName = "Margarita"
        val expectedData = mutableListOf(CocktailModel("1", "alcoholic", "Margarita", "", listOf("ingredient")))

        // Given
        coEvery { searchCocktailByNameUseCase.invoke(cocktailName.lowercase()) } returns flow { emit(Resource(Status.SUCCESS, expectedData)) }

        // When
        viewModel.searchCocktail(cocktailName.lowercase())

        advanceUntilIdle()

        // Collect the state flow value
        val uiState = viewModel.uiState.first()

        // Then
        TestCase.assertEquals(expectedData, uiState.data)
        TestCase.assertEquals(uiState.isError, "")
        TestCase.assertFalse(uiState.isLoading)
    }

    @Test
    fun `searchCocktail returns loading`() = runTest {
        val cocktailName = "Mojito"

        // Given
        coEvery { searchCocktailByNameUseCase.invoke(cocktailName.lowercase()) } returns flow { emit(Resource(Status.LOADING)) }

        viewModel.searchCocktail(cocktailName.lowercase())

        val uiState = viewModel.uiState.value
        Assert.assertTrue(uiState.isLoading)
    }

    @Test
    fun `searchCocktail returns error`() = runTest {
        val cocktailName = "Mojito"

        // Given
        coEvery { searchCocktailByNameUseCase.invoke(cocktailName.lowercase()) } returns flow { emit(Resource(Status.ERROR)) }

        // When
        viewModel.searchCocktail(cocktailName)

        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        Assert.assertEquals("Unexpected error", uiState.isError)
        Assert.assertEquals(emptyList<String>(), uiState.data)
        Assert.assertFalse(uiState.isLoading)
    }
}