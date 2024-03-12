package com.example.vkproductapp.data.repository

import com.example.vkproductapp.data.model.Category
import com.example.vkproductapp.data.service.CategoryService
import com.example.vkproductapp.domain.repository.CategoryRepository

class CategoryRepositoryImpl(private val categoryService: CategoryService): CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return categoryService.getCategories()
    }
}