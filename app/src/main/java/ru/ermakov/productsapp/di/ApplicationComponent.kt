package ru.ermakov.productsapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ermakov.productsapp.presentation.screen.productDetails.ProductDetailsFragment
import ru.ermakov.productsapp.presentation.screen.products.ProductsFragment
import javax.inject.Singleton

@Component(
    modules = [
        RemoteModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class
    ]
)
@Singleton
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(fragment: ProductsFragment)
    fun inject(fragment: ProductDetailsFragment)
}