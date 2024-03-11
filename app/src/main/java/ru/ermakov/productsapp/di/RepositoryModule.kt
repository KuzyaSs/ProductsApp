package ru.ermakov.productsapp.di

import dagger.Module
import dagger.Provides
import ru.ermakov.productsapp.data.remote.dataSource.CategoryRemoteDataSource
import ru.ermakov.productsapp.data.remote.dataSource.ProductRemoteDataSource
import ru.ermakov.productsapp.data.remote.dataSource.ProductRemoteDataSourceImpl
import ru.ermakov.productsapp.data.repository.CategoryRepositoryImpl
import ru.ermakov.productsapp.data.repository.ProductRepositoryImpl
import ru.ermakov.productsapp.domain.repository.CategoryRepository
import ru.ermakov.productsapp.domain.repository.ProductRepository

@Module
class RepositoryModule {
    @Provides
    fun provideCategoryRepository(
        categoryRemoteDataSource: CategoryRemoteDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(categoryRemoteDataSource = categoryRemoteDataSource)
    }

    @Provides
    fun provideProductRepository(
        productRemoteDataSource: ProductRemoteDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(productRemoteDataSource = productRemoteDataSource)
    }
}