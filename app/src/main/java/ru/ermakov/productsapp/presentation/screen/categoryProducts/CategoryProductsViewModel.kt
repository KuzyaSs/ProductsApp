package ru.ermakov.productsapp.presentation.screen.categoryProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ermakov.productsapp.domain.useCase.GetProductPageByCategoryUseCase
import ru.ermakov.productsapp.presentation.Constants.DEFAULT_SKIP_VALUE
import ru.ermakov.productsapp.presentation.Constants.LOAD_PRODUCT_PAGE_DELAY

class CategoryProductsViewModel(
    private val getProductPageByCategoryUseCase: GetProductPageByCategoryUseCase
) : ViewModel() {
    private val _categoryProductsUiState = MutableLiveData(CategoryProductsUiState())
    val categoryProductsUiState: LiveData<CategoryProductsUiState> = _categoryProductsUiState

    private val loadProductPageJob: Job? = null

    fun setUpCategoryProductsScreen(category: String) {
        loadProductPageJob?.cancel()
        _categoryProductsUiState.value = _categoryProductsUiState.value?.copy(
            isLoading = true,
            isError = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _categoryProductsUiState.postValue(
                    _categoryProductsUiState.value?.copy(
                        products = getProductPageByCategoryUseCase(
                            category = category,
                            skip = DEFAULT_SKIP_VALUE
                        ),
                        isRefreshing = false,
                        isLoading = false,
                        isError = false,
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exception.message.toString()
                _categoryProductsUiState.postValue(
                    _categoryProductsUiState.value?.copy(
                        isRefreshing = false,
                        isLoading = false,
                        isError = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshCategoryProductsScreen(category: String) {
        _categoryProductsUiState.value = _categoryProductsUiState.value?.copy(isRefreshing = true)
        setUpCategoryProductsScreen(category = category)
    }

    fun loadProductPage(category: String) {
        if (_categoryProductsUiState.value?.isLoading == true) {
            return
        }
        loadProductPageJob?.cancel()

        _categoryProductsUiState.value = _categoryProductsUiState.value?.copy(
            isLoading = true,
            isError = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(LOAD_PRODUCT_PAGE_DELAY)
                val currentProducts = _categoryProductsUiState.value?.products ?: emptyList()
                _categoryProductsUiState.postValue(
                    _categoryProductsUiState.value?.copy(
                        products = currentProducts + getProductPageByCategoryUseCase(
                            category = category,
                            skip = currentProducts.size.toLong()
                        ),
                        isRefreshing = false,
                        isLoading = false,
                        isError = false
                    )
                )
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    val errorMessage = exception.message.toString()
                    _categoryProductsUiState.postValue(
                        _categoryProductsUiState.value?.copy(
                            isRefreshing = false,
                            isLoading = false,
                            isError = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }

    fun clearErrorMessage() {
        _categoryProductsUiState.value = _categoryProductsUiState.value?.copy(
            isError = false,
            errorMessage = ""
        )
    }
}