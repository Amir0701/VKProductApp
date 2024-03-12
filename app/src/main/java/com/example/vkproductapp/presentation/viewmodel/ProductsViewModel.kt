package com.example.vkproductapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkproductapp.data.model.Category
import com.example.vkproductapp.data.model.Product
import com.example.vkproductapp.data.model.ResponseData
import com.example.vkproductapp.domain.repository.CategoryRepository
import com.example.vkproductapp.domain.repository.ProductRepository
import com.example.vkproductapp.presentation.common.Result
import com.example.vkproductapp.util.InternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductsViewModel(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val internetConnection: InternetConnection): ViewModel() {
    private val _productsLiveData = MutableLiveData<Result<List<Product>>>()
    val productsLiveData: LiveData<Result<List<Product>>> = _productsLiveData
    private val _categoriesLiveData = MutableLiveData<Result<List<String>>>()
    val categoryLiveData: LiveData<Result<List<String>>> = _categoriesLiveData
    private var responseData: ResponseData? = null
    private val limit = 20
    private var currentPage = 0
    private var currentTotal: Long = 0
    private var isLoadedAllData = false

    init {
        getProducts()
        getAllCategories()
    }

    fun getProducts() = viewModelScope.launch(Dispatchers.IO) {
        if(internetConnection.hasInternetConnection()){
            if(!isLoadedAllData){
                _productsLiveData.postValue(Result.Loading())
                try {
                    val response = productRepository.getProducts(currentPage * limit)
                    var responseResult = processResponse(response)
                    if(responseResult is Result.Success){
                        responseData = response.body()
                        if(responseResult.data?.isNotEmpty() == true){
                            currentPage++
                        }
                        currentTotal += responseData?.products?.size ?: 0
                        responseResult.data?.let {
                            productRepository.addToProducts(it)
                            if(currentTotal >= (responseData?.total ?: 0))
                                isLoadedAllData = true
                        }

                        responseResult = Result.Success(productRepository.getSavedProducts())
                    }
                    _productsLiveData.postValue(responseResult)
                }catch (ex: Exception){
                    _productsLiveData.postValue(Result.Error("Request failed"))
                }
            }
        }
        else{
            _productsLiveData.postValue(Result.NoInternetConnection())
        }
    }

    fun searchProduct(query: String) = viewModelScope.launch(Dispatchers.IO){
        if(internetConnection.hasInternetConnection()){
            try {
                _productsLiveData.postValue(Result.Loading())
                val response = productRepository.searchProduct(query)
                val state = processResponse(response)
                _productsLiveData.postValue(state)
            }catch (ex: Exception){
                _productsLiveData.postValue(Result.Error("Request failed"))
            }
        }else{
            _productsLiveData.postValue(Result.NoInternetConnection())
        }
    }

    private fun processResponse(response: Response<ResponseData>): Result<List<Product>> {
        if(response.isSuccessful){
            response.body()?.let { data->
                return Result.Success(data.products)
            }
        }

        return Result.Error(response.message())
    }

    private fun getAllCategories() = viewModelScope.launch(Dispatchers.IO){
        if(internetConnection.hasInternetConnection()){
            try {
                val categoryResponse = categoryRepository.getCategories()
                val categoryResult = processCategoryResponse(categoryResponse)
                _categoriesLiveData.postValue(categoryResult)
            }catch (ex: Exception){
                Log.e("error", ex.message.toString())
            }
        }
    }

    private fun processCategoryResponse(response: Response<List<String>>): Result<List<String>> {
        if(response.isSuccessful){
            response.body()?.let { data->
                return Result.Success(data)
            }
        }

        return Result.Error(response.message())
    }

    fun getProductsByCategory(category: String) = viewModelScope.launch(Dispatchers.IO){
        if(internetConnection.hasInternetConnection()){
            try {
                _productsLiveData.postValue(Result.Loading())
                val response = productRepository.getProductsByCategory(category)
                val state = processResponse(response)
                _productsLiveData.postValue(state)
            }catch (ex: Exception){
                _productsLiveData.postValue(Result.Error("Request failed"))
            }
        }else{
            _productsLiveData.postValue(Result.NoInternetConnection())
        }
    }

    fun getSavedProducts() = viewModelScope.launch(Dispatchers.IO) {
        _productsLiveData.postValue(Result.Success(productRepository.getSavedProducts()))
    }
}