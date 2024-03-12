package com.example.vkproductapp.domain.repository

import com.example.vkproductapp.data.model.Category
import retrofit2.Response

interface CategoryRepository {
    suspend fun getCategories(): Response<List<String>>
}