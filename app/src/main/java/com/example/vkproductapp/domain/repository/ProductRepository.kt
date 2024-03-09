package com.example.vkproductapp.domain.repository

import com.example.vkproductapp.data.model.ResponseData

interface ProductRepository {
    suspend fun getProducts(): ResponseData
}