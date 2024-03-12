package ru.ermakov.productsapp.presentation.screen.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ermakov.productsapp.domain.useCase.GetAllCategoriesUseCase
import ru.ermakov.productsapp.domain.useCase.GetProductPageBySearchQueryUseCase
import ru.ermakov.productsapp.presentation.Constants.DEFAULT_SKIP_VALUE
import ru.ermakov.productsapp.presentation.Constants.LOAD_PRODUCT_PAGE_DELAY
import ru.ermakov.productsapp.presentation.Constants.SEARCH_PRODUCT_PAGE_DELAY

class ProductsViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getProductPageBySearchQueryUseCase: GetProductPageBySearchQueryUseCase
) : ViewModel() {
    private val _productsUiState = MutableLiveData(ProductsUiState())
    val productsUiState: LiveData<ProductsUiState> = _productsUiState

    private var searchProductPageJob: Job? = null
    private var loadProductPageJob: Job? = null

    init {
        searchProductPage()
    }

    fun searchProductPage(searchQuery: String = "") {
        if (searchQuery == _productsUiState.value?.lastSearchQuery
            && _productsUiState.value?.isRefreshing == false
        ) {
            return
        }
        searchProductPageJob?.cancel()
        loadProductPageJob?.cancel()

        _productsUiState.value = _productsUiState.value?.copy(
            products = emptyList(),
            lastSearchQuery = searchQuery,
            isRefreshing = false,
            isLoading = true,
            isError = false
        )
        searchProductPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(SEARCH_PRODUCT_PAGE_DELAY)
                val categories =
                    if (_productsUiState.value?.categories.isNullOrEmpty()) getAllCategoriesUseCase()
                    else _productsUiState.value?.categories ?: emptyList()

                _productsUiState.postValue(
                    _productsUiState.value?.copy(
                        categories = categories,
                        products = getProductPageBySearchQueryUseCase(
                            searchQuery = searchQuery,
                            skip = DEFAULT_SKIP_VALUE
                        ),
                        isLoading = false,
                        isError = false
                    )
                )
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    val errorMessage = exception.message.toString()
                    _productsUiState.postValue(
                        _productsUiState.value?.copy(
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

    fun refreshProductsScreen(searchQuery: String) {
        _productsUiState.value = _productsUiState.value?.copy(isRefreshing = true)
        searchProductPage(searchQuery = searchQuery)
    }

    fun loadProductPage(searchQuery: String) {
        if (_productsUiState.value?.isLoading == true) {
            return
        }
        loadProductPageJob?.cancel()

        _productsUiState.value = _productsUiState.value?.copy(
            isLoading = true,
            isError = false
        )
        loadProductPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(LOAD_PRODUCT_PAGE_DELAY)
                val currentProducts = _productsUiState.value?.products ?: emptyList()
                _productsUiState.postValue(
                    _productsUiState.value?.copy(
                        products = currentProducts + getProductPageBySearchQueryUseCase(
                            searchQuery = searchQuery,
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
                    _productsUiState.postValue(
                        _productsUiState.value?.copy(
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

    fun setUpSearchMode(isSearchMode: Boolean) {
        _productsUiState.value = _productsUiState.value?.copy(isSearchMode = isSearchMode)
    }

    fun clearErrorMessage() {
        _productsUiState.value = _productsUiState.value?.copy(
            isError = false,
            errorMessage = ""
        )
    }
}