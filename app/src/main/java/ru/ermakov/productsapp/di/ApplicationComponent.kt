package ru.ermakov.productsapp.di

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component
import ru.ermakov.productsapp.presentation.screen.products.ProductsFragment
import javax.inject.Singleton

@Component(modules = [RemoteModule::class])
@Singleton
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(fragment: Fragment)
}