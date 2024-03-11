package com.example.vkproductapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkproductapp.data.model.ResponseData
import com.example.vkproductapp.domain.repository.ProductRepository
import com.example.vkproductapp.presentation.common.Result
import com.example.vkproductapp.util.InternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.concurrent.locks.Lock

class ProductsViewModel(
    private val productRepository: ProductRepository,
    private val internetConnection: InternetConnection): ViewModel() {
    private val _productsLiveData = MutableLiveData<Result<ResponseData>>()
    val productsLiveData: LiveData<Result<ResponseData>> = _productsLiveData
    private val limit = 20
    private var currentPage = 0
    var currentTotal: Long = 0
    private var isLoadedAllData = false

    init {
        getProducts()
    }

    fun getProducts() = viewModelScope.launch(Dispatchers.IO) {
        if(internetConnection.hasInternetConnection()){
            if(!isLoadedAllData){
                _productsLiveData.postValue(Result.Loading())
                val response = productRepository.getProducts(currentPage * limit)
                val responseResult = processResponse(response)
                if(responseResult is Result.Success){
                    if(responseResult.data?.products?.isNotEmpty() == true){
                        currentPage++
                    }
                    currentTotal += responseResult.data?.products?.size ?: 0
                    responseResult.data?.let {
                        if(currentTotal >= it.total)
                            isLoadedAllData = true
                    }
                }
                _productsLiveData.postValue(responseResult)
            }
        }
        else{
            _productsLiveData.postValue(Result.NoInternetConnection())
        }
    }

    fun searchProduct(query: String) = viewModelScope.launch(Dispatchers.IO){
        if(internetConnection.hasInternetConnection()){
            _productsLiveData.postValue(Result.Loading())
            val response = productRepository.searchProduct(query)
            val state = processResponse(response)
            _productsLiveData.postValue(state)
        }else{
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