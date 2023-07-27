package com.example.assignment_ankan.network.addProduct

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddProductRetroInstance {
    companion object{
        fun addProduct():Retrofit{
            return Retrofit.Builder()
                .baseUrl("https://app.getswipe.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}