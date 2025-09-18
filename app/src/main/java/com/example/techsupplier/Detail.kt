package com.example.techsupplier


data class Detail(
    val name: String = "",
    val price: Int = 0,
    val category: String = "",
    val info: String = "",
    val phone: String = "",
    val company: Company = Company()
)
