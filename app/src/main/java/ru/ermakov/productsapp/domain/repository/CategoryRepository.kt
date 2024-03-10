package ru.ermakov.productsapp.domain.repository

interface CategoryRepository {
    suspend fun getAllCategories(): List<String>
}