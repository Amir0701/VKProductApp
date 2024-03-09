package com.example.vkproductapp.data.model

data class ResponseData(
    val products: List<Product>,
    val total: Long,
    val skip: Long,
    val limit: Long
)
