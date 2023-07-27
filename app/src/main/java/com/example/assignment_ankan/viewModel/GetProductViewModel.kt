package com.example.assignment_ankan.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment_ankan.model.GetProductResponse
import com.example.assignment_ankan.network.getProduct.GetProductRetroInstance
import com.example.assignment_ankan.network.getProduct.GetProductRetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class GetProductViewModel:ViewModel() {
    private var getProductLiveData = MutableLiveData<ArrayList<GetProductResponse>>()
    init {
        getProductLiveData = MutableLiveData()
    }
    fun getProductObserver():MutableLiveData<ArrayList<GetProductResponse>>{
        return getProductLiveData
    }
    fun makeApiCall(){
        viewModelScope.launch(Dispatchers.IO){
            val retroInstance = GetProductRetroInstance.getProduct().create(GetProductRetroService::class.java)
            val response = retroInstance.fetchAllProducts()
            getProductLiveData.postValue(response)
        }
    }
}