package ru.ermakov.productsapp.presentation.screen.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ermakov.productsapp.domain.useCase.GetAllCategoriesUseCase
import ru.ermakov.productsapp.domain.useCase.GetProductPageUseCase

private const val LOAD_PRODUCT_PAGE_DELAY = 1000L
private const val DEFAULT_SKIP_VALUE = 0L

class ProductsViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getProductPageUseCase: GetProductPageUseCase
) : ViewModel() {
    private val _productsUiState = MutableLiveData(ProductsUiState())
    val productsUiState: LiveData<ProductsUiState> = _productsUiState

    private var loadProductPageJob: Job? = null

    init {
        setUpProductsScreen()
    }

    fun refreshProductsScreen() {
        _productsUiState.value = _productsUiState.value?.copy(isRefreshing = true)
        setUpProductsScreen()
    }

    fun loadProductPage() {
        if (_productsUiState.value?.isLoading == true) {
            return
        }
        loadProductPageJob?.cancel()

        _productsUiState.value = _productsUiState.value?.copy(
            isLoading = true,
            isErrorMessage = false
        )
        loadProductPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(LOAD_PRODUCT_PAGE_DELAY)
                val currentProduct = _productsUiState.value?.products ?: listOf()
                _productsUiState.postValue(
                    _productsUiState.value?.copy(
                        products = currentProduct + getProductPageUseCase(
                            skip = currentProduct.size.toLong()
                        ),
                        isRefreshing = false,
                        isLoading = false,
                        isErrorMessage = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exception.message.toString()
                _productsUiState.postValue(
                    _productsUiState.value?.copy(
                        isRefreshing = false,
                        isLoading = false,
                        isErrorMessage = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun clearErrorMessage() {
        _productsUiState.value = _productsUiState.value?.copy(
            isErrorMessage = false,
            errorMessage = ""
        )
    }

    private fun setUpProductsScreen() {
        loadProductPageJob?.cancel()
        _productsUiState.value = _productsUiState.value?.copy(
            isLoading = true,
            isErrorMessage = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _productsUiState.postValue(
                    _productsUiState.value?.copy(
                        categories = getAllCategoriesUseCase(),
                        products = getProductPageUseCase(skip = DEFAULT_SKIP_VALUE),
                        isRefreshing = false,
                        isLoading = false,
                        isErrorMessage = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exception.message.toString()
                _productsUiState.postValue(
                    _productsUiState.value?.copy(
                        isRefreshing = false,
                        isLoading = false,
                        isErrorMessage = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }
}