package com.karolina.jetpack.foodorder.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karolina.jetpack.foodorder.R
import com.karolina.jetpack.foodorder.data.UiState
import com.karolina.jetpack.foodorder.data.samples.samplePayment
import com.karolina.jetpack.foodorder.ui.theme.Green800

@Composable
fun PaymentScreen(
    data: UiState.Payment,
    onClose: () -> Unit = {},
    onPayClick: () -> Unit = {},
) {

    val roundedDouble by remember {
        val totalAmount = data.orderList.sumOf { it.item.price.toDouble() * it.count }
        val rounded = String.format("%.2f", totalAmount)
        mutableStateOf(rounded)
    }

    Column {
        PaymentHeader(onClose = onClose)
        PaymentCardDetail()
        PaymentAddress(address = data.userData.address)
        PaymentTotalCost(totalAmount = roundedDouble)
        PaymentButton(onPayClick = onPayClick)
    }
}

@Composable
fun PaymentButton(onPayClick: () -> Unit = {}) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = { onPayClick() },
        colors = ButtonDefaults.buttonColors(Green800),
        shape = RoundedCornerShape(10)
    ) {
        Text(
            text = "Pay",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.White
        )

    }
}

@Composable
fun PaymentTotalCost(totalAmount: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Cost",
            color = Color.LightGray,
            fontWeight = FontWeight.Light,
            fontSize = 25.sp
        )
        Text(
            text = totalAmount,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
    }
}

@Composable
fun PaymentAddress(address: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.padding(end = 16.dp),
                    painter = painterResource(id = R.drawable.ic_place),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Black)
                )
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "Address", fontWeight = FontWeight.Bold)
                    Text(text = address, fontWeight = FontWeight.Light)
                }
            }
        }
        IconButton(
            modifier = Modifier.border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(10)
            ),
            onClick = { }
        ) {
            Icon(
                modifier = Modifier.size(25.dp, 25.dp),
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null
            )
        }
    }
}

@Composable
fun PaymentCardDetail() {

    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 16.dp),
        shadowElevation = 3.dp,
        shape = RoundedCornerShape(10)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .size(35.dp, 35.dp),
                painter = painterResource(id = R.drawable.ic_visa_logo),
                contentDescription = null
            )
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "**** 1234")
                Text(text = "Payment method")
            }
        }
    }

}

@Composable
fun PaymentHeader(onClose: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 20.dp),
            text = "Payment",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier.clickable { onClose() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(15.dp, 15.dp),
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PaymentHeaderPreview() {
    PaymentHeader()
}

@Preview(showBackground = true)
@Composable
fun PaymentCardDetailPreview() {
    PaymentCardDetail()
}

@Preview(showBackground = true)
@Composable
fun PaymentAddressPreview() {
    PaymentAddress(address = "Narutowicza 24")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentScreenPreview() {
      PaymentScreen(data = samplePayment)
}