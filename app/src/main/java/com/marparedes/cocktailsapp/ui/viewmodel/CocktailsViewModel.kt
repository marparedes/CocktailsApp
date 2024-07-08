package com.marparedes.cocktailsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marparedes.cocktailsapp.common.Resource
import com.marparedes.cocktailsapp.common.Status
import com.marparedes.cocktailsapp.domain.use_case.SearchCocktailByNameUseCase
import com.marparedes.cocktailsapp.ui.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsViewModel @Inject constructor(
    private val searchCocktailByNameUseCase: SearchCocktailByNameUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun searchCocktail(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchCocktailByNameUseCase.invoke(name.lowercase()).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        if(it.data.isNullOrEmpty()) {
                            _uiState.value = UiState(isError = "No matches found")
                        } else {
                            _uiState.value = UiState(data = it.data)
                        }
                    }
                    Status.LOADING ->  _uiState.value = UiState(isLoading = true)
                    Status.API_ERROR,
                    Status.NETWORK_ERROR,
                    Status.ERROR -> _uiState.value = UiState(isError = "error")
                }
            }
        }
    }
}