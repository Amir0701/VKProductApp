package com.example.vkproductapp.data.repository

import com.example.vkproductapp.data.model.Category
import com.example.vkproductapp.data.service.CategoryService
import com.example.vkproductapp.domain.repository.CategoryRepository
import retrofit2.Response

class CategoryRepositoryImpl(private val categoryService: CategoryService): CategoryRepository {
    override suspend fun getCategories(): Response<List<String>> {
        return categoryService.getCategories()
    }
}