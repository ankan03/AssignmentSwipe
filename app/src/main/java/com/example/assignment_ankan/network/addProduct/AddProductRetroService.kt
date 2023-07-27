package com.example.assignment_ankan.network.addProduct

import com.example.assignment_ankan.model.AddProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AddProductRetroService {
    @Multipart
    @POST("/api/public/add")
    suspend fun addProduct(
        @Part("product_name") product_name: RequestBody,
        @Part("product_type") product_type: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody,
        @Part image: MultipartBody.Part?
    ): AddProductResponse
}