package com.example.assignment_ankan.network.getProduct

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetProductRetroInstance {
    companion object{
        fun getProduct():Retrofit{
            return Retrofit.Builder()
                .baseUrl("https://app.getswipe.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}