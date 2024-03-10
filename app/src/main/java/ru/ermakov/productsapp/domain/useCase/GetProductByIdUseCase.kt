package ru.ermakov.productsapp.domain.useCase

import ru.ermakov.productsapp.domain.model.Product
import ru.ermakov.productsapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(id: Long): Product {
        return productRepository.getProductById(id = id)
    }
}