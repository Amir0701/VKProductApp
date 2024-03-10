package com.example.vkproductapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkproductapp.data.model.ResponseData
import com.example.vkproductapp.domain.repository.ProductRepository
import com.example.vkproductapp.util.InternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import com.example.vkproductapp.presentation.common.Result
class ProductsViewModel(
    private val productRepository: ProductRepository,
    private val internetConnection: InternetConnection): ViewModel() {
    private val _productsLiveData = MutableLiveData<Result<ResponseData>>()
    val productsLiveData: LiveData<Result<ResponseData>> = _productsLiveData

    fun getProducts() = viewModelScope.launch(Dispatchers.IO) {
        if(internetConnection.hasInternetConnection()){
            _productsLiveData.postValue(Result.Loading())
            val response = productRepository.getProducts()
            val responseResult = processResponse(response)
            _productsLiveData.postValue(responseResult)
        }
        else{
            _productsLiveData.postValue(Result.NoInternetConnection())
        }
    }

    private fun processResponse(response: Response<ResponseData>): Result<ResponseData> {
        if(response.isSuccessful){
            response.body()?.let { data->
                return Result.Success(data)
            }
        }

        return Result.Error(response.message())
    }
}