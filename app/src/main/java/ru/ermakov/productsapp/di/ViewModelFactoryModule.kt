package ru.ermakov.productsapp.di

import dagger.Module
import dagger.Provides
import ru.ermakov.productsapp.domain.useCase.GetAllCategoriesUseCase
import ru.ermakov.productsapp.domain.useCase.GetProductPageUseCase
import ru.ermakov.productsapp.presentation.screen.products.ProductsViewModelFactory

@Module
class ViewModelFactoryModule {
    @Provides
    fun provideProductsViewModelFactory(
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
        getProductPageUseCase: GetProductPageUseCase
    ): ProductsViewModelFactory {
        return ProductsViewModelFactory(
            getAllCategoriesUseCase = getAllCategoriesUseCase,
            getProductPageUseCase = getProductPageUseCase
        )
    }
}