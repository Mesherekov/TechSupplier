package com.example.techsupplier

data class Detail(
    val imageUrl: String,
    val name: String,
    val price: Int,
    val category: List<String>,
    val info: String,
    val phone: String,
    val company: Company
)
