package com.example.assignment_ankan.network.getProduct

import com.example.assignment_ankan.model.GetProductResponse
import retrofit2.http.GET
import java.util.*

interface GetProductRetroService {
    @GET("/api/public/get")
    suspend fun fetchAllProducts(): ArrayList<GetProductResponse>
}