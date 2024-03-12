package ru.ermakov.productsapp.data.remote.dataSource

import android.util.Log
import ru.ermakov.productsapp.data.remote.api.ProductApi
import ru.ermakov.productsapp.data.remote.exception.ApiExceptionHandler
import ru.ermakov.productsapp.data.remote.model.toProduct
import ru.ermakov.productsapp.domain.model.Product

private const val LIMIT = 20L

class ProductRemoteDataSourceImpl(
    private val productApi: ProductApi,
    private val apiExceptionHandler: ApiExceptionHandler
) : ProductRemoteDataSource {
    override suspend fun getProductPage(skip: Long): List<Product> {
        val productsResponse = productApi.getProductPage(limit = LIMIT, skip = skip)
        if (productsResponse.isSuccessful) {
            Log.d("MY_TAG", "SUCCESS getProductPage ${productsResponse.body()}")
            productsResponse.body()?.let { remoteProducts ->
                return remoteProducts.products.map { remoteProduct -> remoteProduct.toProduct() }
            }
        }
        Log.d("MY_TAG", "ERROR getProductPage ${productsResponse.errorBody()}")
        throw apiExceptionHandler.handleApiException(response = productsResponse)
    }

    override suspend fun getProductPageByCategory(category: String, skip: Long): List<Product> {
        val productsResponse = productApi.getProductPageByCategory(
            category = category,
            limit = LIMIT,
            skip = skip
        )
        if (productsResponse.isSuccessful) {
            productsResponse.body()?.let { remoteProducts ->
                return remoteProducts.products.map { remoteProduct -> remoteProduct.toProduct() }
            }
        }
        throw apiExceptionHandler.handleApiException(response = productsResponse)
    }

    override suspend fun getProductPageBySearchQuery(
        searchQuery: String,
        skip: Long
    ): List<Product> {
        val productsResponse = productApi.getProductPageBySearchQuery(
            searchQuery = searchQuery,
            limit = LIMIT,
            skip = skip
        )
        if (productsResponse.isSuccessful) {
            productsResponse.body()?.let { remoteProducts ->
                return remoteProducts.products.map { remoteProduct -> remoteProduct.toProduct() }
            }
        }
        throw apiExceptionHandler.handleApiException(response = productsResponse)
    }

    override suspend fun getProductById(id: Long): Product {
        val productResponse = productApi.getProductById(id = id)
        if (productResponse.isSuccessful) {
            Log.d("MY_TAG", "SUCCESS getProductById ${productResponse.body()}")
            productResponse.body()?.let { remoteProduct ->
                return remoteProduct.toProduct()
            }
        }
        Log.d("MY_TAG", "ERROR getProductById ${productResponse.errorBody()}")
        throw apiExceptionHandler.handleApiException(response = productResponse)
    }
}