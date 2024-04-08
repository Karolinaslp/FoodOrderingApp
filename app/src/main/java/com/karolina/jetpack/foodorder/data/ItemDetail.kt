package com.karolina.jetpack.foodorder.data

import androidx.annotation.DrawableRes
import com.karolina.jetpack.foodorder.R

data class ItemDetail(
    val id: Long,
    val orderState: String,
    val name: String,
    val date: String,
    val ingredients: String,
    val calories: String = "Calories: 200kcal/100g",
    val price: Float,
    @DrawableRes val image: Int = R.drawable.pizza_one,
    val hashTags: List<String> = listOf("Vegan", "Hot", "Extra Hot")
)
