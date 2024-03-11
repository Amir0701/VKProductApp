package com.example.vkproductapp.data.service

import com.example.vkproductapp.data.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("/products")
    suspend fun getProducts(@Query("skip") skip: Int, @Query("limit") limit:Int = 20): Response<ResponseData>

    @GET("/products/search")
    suspend fun searchProduct(@Query("q") query: String): Response<ResponseData>
}