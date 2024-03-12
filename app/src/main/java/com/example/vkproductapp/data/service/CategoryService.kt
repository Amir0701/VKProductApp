package com.example.vkproductapp.data.service

import com.example.vkproductapp.data.model.Category
import retrofit2.Response
import retrofit2.http.GET


interface CategoryService {
    @GET("/products/categories")
    suspend fun getCategories(): Response<List<String>>
}