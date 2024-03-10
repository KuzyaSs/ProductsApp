package ru.ermakov.productsapp.data.repository

import ru.ermakov.productsapp.data.remote.dataSource.ProductRemoteDataSource
import ru.ermakov.productsapp.domain.model.Product
import ru.ermakov.productsapp.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource
) : ProductRepository {
    override suspend fun getProductPage(skip: Long): List<Product> {
        return productRemoteDataSource.getProductPage(skip = skip)
    }

    override suspend fun getProductPageByCategory(category: String, skip: Long): List<Product> {
        return productRemoteDataSource.getProductPageByCategory(category = category, skip = skip)
    }

    override suspend fun getProductPageBySearchQuery(
        searchQuery: String,
        skip: Long
    ): List<Product> {
        return productRemoteDataSource.getProductPageBySearchQuery(
            searchQuery = searchQuery,
            skip = skip
        )
    }

    override suspend fun getProductById(id: Long): Product {
        return productRemoteDataSource.getProductById(id = id)
    }
}