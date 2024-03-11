package ru.ermakov.productsapp.data.remote.dataSource

import ru.ermakov.productsapp.data.remote.api.CategoryApi
import ru.ermakov.productsapp.data.remote.exception.ApiExceptionHandler

class CategoryRemoteDataSourceImpl(
    private val categoryApi: CategoryApi,
    private val apiExceptionHandler: ApiExceptionHandler
) : CategoryRemoteDataSource {
    override suspend fun getAllCategories(): List<String> {
        val categoriesResponse = categoryApi.getAllCategories()
        if (categoriesResponse.isSuccessful) {
            categoriesResponse.body()?.let { categories ->
                return categories
            }
        }
        throw apiExceptionHandler.handleApiException(response = categoriesResponse)
    }
}