package com.example.assignment_ankan.model

data class AddProductResponse(
    val message: String,
    val product_details: GetProductResponse,
    val product_id: String,
    val success: String
)
