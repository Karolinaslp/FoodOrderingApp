package com.karolina.jetpack.foodorder.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karolina.jetpack.foodorder.R
import com.karolina.jetpack.foodorder.data.ItemDetail
import com.karolina.jetpack.foodorder.data.Order
import com.karolina.jetpack.foodorder.data.samples.sampleShoppingBag
import com.karolina.jetpack.foodorder.ui.theme.Green800

@Composable
fun ShoppingBagScreen(
    shoppingList: List<Order>,
    roundedDouble: Double = 0.0,
    onDecrementOrderNumber: (ItemDetail) -> Unit = {},
    onIncrementOrderNumber: (ItemDetail) -> Unit = {},
    onPaymentClick: () -> Unit = {}
) {
    Column() {
        Box(modifier = Modifier.padding(start = 16.dp, top = 25.dp)) {
            Text(text = "Basket", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        }
        ShoppingBagList(
            modifier = Modifier.weight(1f),
            shoppingList = shoppingList,
            onIncrementOrderNumber = onDecrementOrderNumber,
            onDecrementOrderNumber = onIncrementOrderNumber
        )
        SumUp(roundedDouble = roundedDouble)
    }
}

@Composable
fun SumUp(
    roundedDouble: Double,
    onPaymentClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SumUpRowText(
            textSize = 18.sp,
            fontWeight = FontWeight.Light,
            leftText = "Cost",
            rightText = roundedDouble.toString()
        )
        SumUpRowText(
            textSize = 18.sp,
            fontWeight = FontWeight.Light,
            leftText = "Delivery Cost",
            rightText = "10"
        )
    }
    SumUpRowText(
        textSize = 18.sp,
        fontWeight = FontWeight.Light,
        leftText = "Total Cost",
        rightText = (roundedDouble + 10).toString()
    )

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = onPaymentClick,
        colors = ButtonDefaults.buttonColors(Green800)
    ) {
        Text(modifier = Modifier.padding(10.dp), text = "Payment", color = Color.White)
    }
}

@Composable
fun SumUpRowText(
    textSize: TextUnit,
    fontWeight: FontWeight,
    leftText: String,
    rightText: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = leftText,
            fontSize = textSize,
            fontWeight = fontWeight,
        )
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = rightText,
            fontWeight = fontWeight,
            fontSize = textSize,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun ShoppingBagList(
    modifier: Modifier = Modifier,
    shoppingList: List<Order>,
    onIncrementOrderNumber: (ItemDetail) -> Unit = {},
    onDecrementOrderNumber: (ItemDetail) -> Unit = {}
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(bottom = 100.dp)) {
        items(items = shoppingList) { order ->
            ShoppingBagItem(
                order = order,
                onIncrementOrderNumber = onIncrementOrderNumber,
                onDecrementOrderNumber = onDecrementOrderNumber
            )

        }
    }
}

@Composable
fun ShoppingBagItem(
    order: Order,
    onIncrementOrderNumber: (ItemDetail) -> Unit = {},
    onDecrementOrderNumber: (ItemDetail) -> Unit = {}
) {
    val pizzaImage = order.item.image

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        tonalElevation = 1.dp,
        shape = RoundedCornerShape(20)
    ) {
        Row {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(125.dp, 125.dp),
                    painter = painterResource(id = pizzaImage),
                    contentDescription = null
                )
                Column {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(bottom = 10.dp),
                        text = order.item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        AmountButton(
                            icon = R.drawable.ic_minus,
                            onClick = { onDecrementOrderNumber(order.item) }
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            fontWeight = FontWeight.Bold,
                            text = order.count.toString(),
                            fontSize = 18.sp
                        )

                        AmountButton(
                            icon = R.drawable.ic_add,
                            onClick = { onIncrementOrderNumber(order.item) }
                        )
                    }
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = order.item.price.toString(),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Composable
fun AmountButton(icon: Int, onClick: () -> Unit) {
    IconButton(
        modifier = Modifier.border(
            BorderStroke(
                1.dp,
                color = Color.LightGray
            ),
            shape = RoundedCornerShape(20)
        ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Green800
        )

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShoppingBagScreenPreview() {
    ShoppingBagScreen(shoppingList = sampleShoppingBag.itemList)
}

@Preview(showBackground = true)
@Composable
fun ShoppingBagItemPreview() {
    ShoppingBagItem(order = sampleShoppingBag.itemList[0])
}