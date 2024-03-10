package ru.ermakov.productsapp.domain.useCase

import ru.ermakov.productsapp.domain.model.Product
import ru.ermakov.productsapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductPageByCategoryUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(category: String, skip: Long): List<Product> {
        return productRepository.getProductPageByCategory(category = category, skip = skip)
    }
}