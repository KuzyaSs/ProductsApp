package ru.ermakov.productsapp.presentation.screen.productDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.productsapp.domain.useCase.GetProductByIdUseCase

class ProductDetailsViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {
    private val _productDetailsUiState = MutableLiveData(ProductDetailsUiState())
    val productDetailsUiState: LiveData<ProductDetailsUiState> = _productDetailsUiState

    fun setUpProductDetailsScreen(productId: Long) {
        _productDetailsUiState.value = _productDetailsUiState.value?.copy(
            isLoading = true,
            isErrorMessage = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _productDetailsUiState.postValue(
                    _productDetailsUiState.value?.copy(
                        product = getProductByIdUseCase(productId = productId),
                        isRefreshing = false,
                        isLoading = false,
                        isErrorMessage = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exception.message.toString()
                _productDetailsUiState.postValue(
                    _productDetailsUiState.value?.copy(
                        isRefreshing = false,
                        isLoading = false,
                        isErrorMessage = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshProductDetails(productId: Long) {
        _productDetailsUiState.value = _productDetailsUiState.value?.copy(isRefreshing = true)
        setUpProductDetailsScreen(productId = productId)
    }

    fun clearErrorMessage() {
        _productDetailsUiState.value = _productDetailsUiState.value?.copy(
            isErrorMessage = false,
            errorMessage = ""
        )
    }
}