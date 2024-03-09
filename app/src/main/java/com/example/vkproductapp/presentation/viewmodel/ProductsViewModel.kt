package com.example.vkproductapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkproductapp.data.model.ResponseData
import com.example.vkproductapp.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class ProductsViewModel(private val productRepository: ProductRepository): ViewModel() {
    private val _productsLiveData = MutableLiveData<ResponseData>()
    val productsLiveData: LiveData<ResponseData> = _productsLiveData

    fun getProducts() = viewModelScope.launch(Dispatchers.IO) {
        val response = productRepository.getProducts()
        try {
            val responseData = processResponse(response)
            responseData?.let {
                _productsLiveData.postValue(it)
            }
        }catch (e: Exception){
            Log.e("TAG", e.message.toString())
        }
    }

    private fun processResponse(response: Response<ResponseData>): ResponseData? {
        if(response.isSuccessful){
            return response.body()
        }

        throw Exception()
    }
}