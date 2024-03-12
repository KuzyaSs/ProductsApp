package ru.ermakov.productsapp.presentation.screen.productDetails

import ru.ermakov.productsapp.domain.model.Product

data class ProductDetailsUiState(
    val product: Product? = null,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isErrorMessage: Boolean = false,
    val errorMessage: String = ""
)