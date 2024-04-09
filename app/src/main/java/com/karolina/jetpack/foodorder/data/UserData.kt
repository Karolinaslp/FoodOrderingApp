package com.karolina.jetpack.foodorder.data

data class UserData(
    val id: Long,
    val name: String,
    val surname: String,
    val address: String,
    val email: String,
    val password: String
)
