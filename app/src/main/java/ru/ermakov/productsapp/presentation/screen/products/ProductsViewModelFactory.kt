package ru.ermakov.productsapp.presentation.screen.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.productsapp.domain.useCase.GetAllCategoriesUseCase
import ru.ermakov.productsapp.domain.useCase.GetProductPageUseCase
import javax.inject.Inject

class ProductsViewModelFactory @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getProductPageUseCase: GetProductPageUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(
                getAllCategoriesUseCase = getAllCategoriesUseCase,
                getProductPageUseCase = getProductPageUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}