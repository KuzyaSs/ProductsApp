package ru.ermakov.productsapp.presentation.screen.products

import ru.ermakov.productsapp.domain.model.Product

data class ProductsUiState(
    val categories: List<String> = listOf(),
    val products: List<Product> = listOf(),
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isErrorMessage: Boolean = false,
    val errorMessage: String = ""
)