package ru.ermakov.productsapp.presentation.screen.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.productsapp.domain.useCase.GetProductByIdUseCase
import javax.inject.Inject

class ProductDetailsViewModelFactory @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            return ProductDetailsViewModel(getProductByIdUseCase = getProductByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}