package ru.ermakov.productsapp.data.repository

import ru.ermakov.productsapp.data.remote.dataSource.CategoryRemoteDataSource
import ru.ermakov.productsapp.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryRemoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {
    override suspend fun getAllCategories(): List<String> {
        return categoryRemoteDataSource.getAllCategories()
    }
}