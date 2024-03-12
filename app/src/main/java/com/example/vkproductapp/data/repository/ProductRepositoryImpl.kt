package com.example.vkproductapp.data.repository

import com.example.vkproductapp.data.model.Product
import com.example.vkproductapp.data.model.ResponseData
import com.example.vkproductapp.data.service.ProductService
import com.example.vkproductapp.domain.repository.ProductRepository
import retrofit2.Response

class ProductRepositoryImpl(private val productService: ProductService): ProductRepository {
    private var products: List<Product> = emptyList()
    override suspend fun getProducts(skip: Int): Response<ResponseData> {
        return productService.getProducts(skip)
    }

    override suspend fun searchProduct(query: String): Response<ResponseData> {
        return productService.searchProduct(query)
    }

    override suspend fun addToProducts(products: List<Product>) {
        val unionProducts = mutableListOf<Product>()
        unionProducts.addAll(this.products)
        unionProducts.addAll(products)
        this.products = unionProducts
    }

    override suspend fun getSavedProducts(): List<Product> {
        return products
    }

    override suspend fun getProductsByCategory(category: String): Response<ResponseData> {
        return productService.getProductsByCategory(category)
    }
}