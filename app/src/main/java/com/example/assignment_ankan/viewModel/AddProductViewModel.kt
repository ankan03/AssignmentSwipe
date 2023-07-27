package com.example.assignment_ankan.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment_ankan.model.AddProductResponse
import com.example.assignment_ankan.network.addProduct.AddProductRetroInstance
import com.example.assignment_ankan.network.addProduct.AddProductRetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*

class AddProductViewModel:ViewModel() {
    private var addProductLiveData = MutableLiveData<AddProductResponse>()
    init {
        addProductLiveData = MutableLiveData()
    }
    fun addProductObserver():MutableLiveData<AddProductResponse>{
        return addProductLiveData
    }
    fun makeApiCall(productName: RequestBody, productType:RequestBody, price:RequestBody, tax:RequestBody, imagePart:MultipartBody.Part?){
        viewModelScope.launch(Dispatchers.IO){
            val retroInstance = AddProductRetroInstance.addProduct().create(AddProductRetroService::class.java)
            val response = retroInstance.addProduct(productName,productType,price,tax,imagePart)
            Log.d("IMAGE_UPLOAD","imagePart: ${imagePart.toString()}")
            addProductLiveData.postValue(response)
        }
    }
}