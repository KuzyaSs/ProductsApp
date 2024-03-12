package ru.ermakov.productsapp.presentation.screen.categoryProducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.productsapp.domain.useCase.GetProductPageByCategoryUseCase
import javax.inject.Inject

class CategoryProductsViewModelFactory @Inject constructor(
    private val getProductPageByCategoryUseCase: GetProductPageByCategoryUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryProductsViewModel::class.java)) {
            return CategoryProductsViewModel(
                getProductPageByCategoryUseCase = getProductPageByCategoryUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}