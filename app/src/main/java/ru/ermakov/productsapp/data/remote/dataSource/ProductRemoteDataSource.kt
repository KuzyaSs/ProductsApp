package ru.ermakov.productsapp.data.remote.dataSource

import ru.ermakov.productsapp.domain.model.Product

interface ProductRemoteDataSource {
    suspend fun getProductPage(skip: Long): List<Product>

    suspend fun getProductPageByCategory(category: String, skip: Long): List<Product>

    suspend fun getProductPageBySearchQuery(searchQuery: String, skip: Long): List<Product>

    suspend fun getProductById(id: Long): Product
}