package ru.ermakov.productsapp.presentation.screen.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.productsapp.domain.useCase.GetAllCategoriesUseCase
import ru.ermakov.productsapp.domain.useCase.GetProductPageUseCase

private const val DEFAULT_SKIP_VALUE = 0L

class ProductsViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getProductPageUseCase: GetProductPageUseCase
) : ViewModel() {
    private val _productsUiState = MutableLiveData(ProductsUiState())
    val productsUiState: LiveData<ProductsUiState> = _productsUiState

    init {
        setUpProductsScreen()
    }

    fun refreshProductsScreen() {
        _productsUiState.value = _productsUiState.value?.copy(isRefreshingShown = true)
        setUpProductsScreen()
    }

    fun loadNextProductPage() {

    }

    private fun setUpProductsScreen() {
        _productsUiState.value = _productsUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _productsUiState.postValue(
                    _productsUiState.value?.copy(
                        categories = getAllCategoriesUseCase(),
                        products = getProductPageUseCase(skip = DEFAULT_SKIP_VALUE),
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exception.message.toString()
                _productsUiState.postValue(
                    _productsUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }
}