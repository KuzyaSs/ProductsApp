package ru.ermakov.productsapp.domain.repository

import ru.ermakov.productsapp.domain.model.Product

interface ProductRepository {
    suspend fun getProductPage(skip: Long): List<Product>

    suspend fun getProductPageByCategory(category: String, skip: Long): List<Product>

    suspend fun getProductPageBySearchQuery(searchQuery: String, skip: Long): List<Product>

    suspend fun getProductById(id: Long): Product
}