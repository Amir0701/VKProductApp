package com.example.vkproductapp.data.service

import com.example.vkproductapp.data.model.ResponseData
import retrofit2.http.GET

interface ProductService {
    @GET("/products")
    fun getProducts(): ResponseData
}