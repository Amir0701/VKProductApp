package com.example.vkproductapp.data.repository

import com.example.vkproductapp.data.model.ResponseData
import com.example.vkproductapp.data.service.ProductService
import com.example.vkproductapp.domain.repository.ProductRepository
import retrofit2.Response

class ProductRepositoryImpl(private val productService: ProductService): ProductRepository {
    override suspend fun getProducts(skip: Int): Response<ResponseData> {
        return productService.getProducts(skip)
    }

    override suspend fun searchProduct(query: String): Response<ResponseData> {
        return productService.searchProduct(query)
    }
}