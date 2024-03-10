package ru.ermakov.productsapp.data.remote.dataSource

interface CategoryRemoteDataSource {
    suspend fun getAllCategories(): List<String>
}