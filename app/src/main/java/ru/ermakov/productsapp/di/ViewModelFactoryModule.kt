package ru.ermakov.productsapp.di

import dagger.Module
import dagger.Provides
import ru.ermakov.productsapp.domain.useCase.GetAllCategoriesUseCase
import ru.ermakov.productsapp.domain.useCase.GetProductPageBySearchQueryUseCase
import ru.ermakov.productsapp.presentation.screen.products.ProductsViewModelFactory

@Module
class ViewModelFactoryModule {
    @Provides
    fun provideProductsViewModelFactory(
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
        getProductPageBySearchQueryUseCase: GetProductPageBySearchQueryUseCase
    ): ProductsViewModelFactory {
        return ProductsViewModelFactory(
            getAllCategoriesUseCase = getAllCategoriesUseCase,
            getProductPageBySearchQueryUseCase = getProductPageBySearchQueryUseCase
        )
    }
}