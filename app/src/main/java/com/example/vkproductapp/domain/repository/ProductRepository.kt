package com.example.vkproductapp.domain.repository

import com.example.vkproductapp.data.model.Product
import com.example.vkproductapp.data.model.ResponseData
import retrofit2.Response

interface ProductRepository {
    suspend fun getProducts(skip: Int): Response<ResponseData>

    suspend fun searchProduct(query: String): Response<ResponseData>

    suspend fun addToProducts(products: List<Product>)

    suspend fun getSavedProducts(): List<Product>
}