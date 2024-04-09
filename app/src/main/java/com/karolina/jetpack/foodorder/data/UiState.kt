package com.karolina.jetpack.foodorder.data



sealed class UiState {
    data class Home(
        val products: List<ItemDetail>,
        val userData: UserData
    )
    data class Profile(
        val name: String,
        val surname: String,
        val email: String
    )
    data class ShoppingBasket(val itemList: List<Order>)
    data class OrderHistory(val orderList: List<Order>)
    data class ItemDetailScreen(
        val item: ItemDetail,
        val alreadyAdded: Boolean = false
    )
    data class Payment(
        val userData: UserData,
        val orderList: List<Order>
    )
    data class Map(
        val name: String,
        val surname: String,
        val sourceAddress: String,
        val targetAddress: String
    )
}