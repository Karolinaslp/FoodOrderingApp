package com.karolina.jetpack.foodorder

import com.karolina.jetpack.foodorder.data.ItemDetail
import com.karolina.jetpack.foodorder.data.Order
import com.karolina.jetpack.foodorder.data.UserData
import com.karolina.jetpack.foodorder.data.samples.sampleItemDetailOne
import com.karolina.jetpack.foodorder.data.samples.sampleItemDetailTwo
import com.karolina.jetpack.foodorder.data.samples.sampleItemDetailZero
import com.karolina.jetpack.foodorder.data.samples.sampleUserOne
import com.karolina.jetpack.foodorder.data.samples.sampleUserTwo
import kotlinx.coroutines.delay

class SecretBackend {
    private val users = listOf(sampleUserOne, sampleUserTwo)
    private val items = listOf(sampleItemDetailZero, sampleItemDetailOne, sampleItemDetailTwo)
    private val history = arrayListOf<Order>()

    suspend fun login(email: String, password: String): UserData? {
        delay(2000)
        return users.find { it.email == email && it.password == password }
    }

    fun getItemDetail(id: Long):  ItemDetail? {
        return items.find { it.id == id }
    }

    fun getAllItems(): List<ItemDetail> {
        return items
    }

    fun saveOrderInHistory(orders: Set<Order>) {
        history.addAll(orders)
    }

    fun getOrdersHistory() = history.toList()
}
