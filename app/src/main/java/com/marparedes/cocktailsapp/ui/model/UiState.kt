package com.marparedes.cocktailsapp.ui.model

import com.marparedes.cocktailsapp.domain.model.CocktailModel

data class UiState(
    var isLoading: Boolean = false,
    var isError: String = "",
    var data: List<CocktailModel>? = emptyList()
)
