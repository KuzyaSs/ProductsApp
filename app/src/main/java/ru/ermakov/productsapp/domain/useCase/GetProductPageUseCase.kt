package ru.ermakov.productsapp.domain.useCase

import ru.ermakov.productsapp.domain.model.Product
import ru.ermakov.productsapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductPageUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(skip: Long): List<Product> {
        return productRepository.getProductPage(skip = skip)
    }
}