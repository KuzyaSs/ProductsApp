package ru.ermakov.productsapp.domain.useCase

import ru.ermakov.productsapp.domain.model.Product
import ru.ermakov.productsapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductPageBySearchQueryUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(searchQuery: String, skip: Long): List<Product> {
        return productRepository.getProductPageBySearchQuery(searchQuery = searchQuery, skip = skip)
    }
}