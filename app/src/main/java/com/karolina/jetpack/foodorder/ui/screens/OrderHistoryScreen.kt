package com.karolina.jetpack.foodorder.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karolina.jetpack.foodorder.R
import com.karolina.jetpack.foodorder.data.Order
import com.karolina.jetpack.foodorder.data.UiState
import com.karolina.jetpack.foodorder.data.samples.sampleEmptyOrderHistoryData
import com.karolina.jetpack.foodorder.data.samples.sampleOrderHistoryData
import com.karolina.jetpack.foodorder.ui.theme.Green800

@Composable
fun OrderHistoryScreen(
    data: UiState.OrderHistory,
    onEmptyHistoryClick: () -> Unit = {}
) {
    when (data.orderList.isEmpty()) {
        true -> EmptyOrderListHistory(onEmptyHistoryClick)
        false -> OrderHistory(data.orderList)
    }
}

@Composable
fun OrderHistory(orderList: List<Order>) {
    Column {
        OrdersHistoryHeader(orderNumber = orderList.size)
        LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)) {
            items(
                items = orderList,
                key = { order: Order -> order.item.id }
            ) { order ->
                OrderHistoryItem(order)
            }
        }
    }
}

@Composable
fun OrderHistoryItem(order: Order) {
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp),
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(10)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "${order.item.orderState}, ${order.item.date}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "${order.item.name}...", fontWeight = FontWeight.Light
            )
            Box(modifier = Modifier.fillMaxWidth(), Alignment.BottomEnd) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = (order.item.price * order.count).toString(),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Green800
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Green800
                    )
                }
            }
        }
    }
}

@Composable
fun OrdersHistoryHeader(orderNumber: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Order history ($orderNumber)",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
    }
}

@Composable
fun EmptyOrderListHistory(onEmptyHistoryClick: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(200.dp, 200.dp),
            painter = painterResource(id = R.drawable.empty_order_history),
            contentDescription = null
        )
        Text(
            text = "You don't have\nany orders yet.",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Place your first order\nto check its details.",
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(Green800),
            onClick = onEmptyHistoryClick,
            shape = RoundedCornerShape(20)
        ) {
            Text(text = "Start order", color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptyHistoryScreenPreview() {
    OrderHistoryScreen(data = sampleEmptyOrderHistoryData)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryScreenPreview() {
    OrderHistoryScreen(data = sampleOrderHistoryData)
}