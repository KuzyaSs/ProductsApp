package ru.ermakov.productsapp.domain.useCase

import ru.ermakov.productsapp.domain.repository.CategoryRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): List<String> {
        return categoryRepository.getAllCategories()
    }
}