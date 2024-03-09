package com.example.vkproductapp.data.repository

import com.example.vkproductapp.data.model.ResponseData
import com.example.vkproductapp.data.service.ProductService
import com.example.vkproductapp.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productService: ProductService): ProductRepository {
    override suspend fun getProducts(): ResponseData {
        return productService.getProducts()
    }
}