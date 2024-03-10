package ru.ermakov.productsapp.app

import android.app.Application
import ru.ermakov.productsapp.di.ApplicationComponent
import ru.ermakov.productsapp.di.DaggerApplicationComponent

class ProductsApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(context = this)
    }
}