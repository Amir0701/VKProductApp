package com.example.vkproductapp.domain.repository

import com.example.vkproductapp.data.model.ResponseData
import retrofit2.Response

interface ProductRepository {
    suspend fun getProducts(skip: Int): Response<ResponseData>
}