package com.marparedes.cocktailsapp.domain.model

import com.marparedes.cocktailsapp.data.remote.dto.DrinkDTO

data class CocktailModel(
    val idDrink: String?,
    val strAlcoholic: String?,
    val strDrink: String?,
    val strDrinkThumb: String?,
    val ingredients: List<String>?
)

fun DrinkDTO.toDomain(): CocktailModel {
    return CocktailModel(
        idDrink = idDrink,
        strAlcoholic = strAlcoholic,
        strDrink = strDrink,
        strDrinkThumb = strDrinkThumb,
        ingredients = strIngredientToList(
            listOf(
                strIngredient1,
                strIngredient2,
                strIngredient3,
                strIngredient4,
                strIngredient5,
                strIngredient6,
                strIngredient7,
                strIngredient8,
                strIngredient9,
                strIngredient10,
                strIngredient11,
                strIngredient12,
                strIngredient13,
                strIngredient14,
                strIngredient15
            )
        )
    )
}

fun strIngredientToList(list: List<String?>): List<String> {
    val newList = mutableListOf<String>()
    list.forEach {
        if (!it.isNullOrBlank()) {
            newList.add(it)
        }
    }
    return newList
}
