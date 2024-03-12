package ru.ermakov.productsapp.presentation.screen.products

import ru.ermakov.productsapp.domain.model.Product

data class ProductsUiState(
    val categories: List<String> = emptyList(),
    val products: List<Product> = emptyList(),
    val lastSearchQuery: String? = null,
    val isSearchMode: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)