package ru.ermakov.productsapp.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.ermakov.productsapp.R

class ProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
    }
}