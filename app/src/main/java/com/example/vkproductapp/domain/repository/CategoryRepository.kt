package com.example.vkproductapp.domain.repository

import com.example.vkproductapp.data.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}