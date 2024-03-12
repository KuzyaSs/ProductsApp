package ru.ermakov.productsapp.presentation.screen.categoryProducts

import ru.ermakov.productsapp.domain.model.Product

data class CategoryProductsUiState(
    val products: List<Product> = emptyList(),
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)