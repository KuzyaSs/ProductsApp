package ru.ermakov.productsapp.presentation.screen.products

import ru.ermakov.productsapp.domain.model.Product

data class ProductsUiState(
    val categories: List<String> = listOf(),
    val products: List<Product> = listOf(),
    val isRefreshingShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)