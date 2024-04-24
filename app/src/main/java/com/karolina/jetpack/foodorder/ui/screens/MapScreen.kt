package com.karolina.jetpack.foodorder.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.karolina.jetpack.foodorder.R
import com.karolina.jetpack.foodorder.data.UiState
import com.karolina.jetpack.foodorder.data.samples.sampleMapData
import com.karolina.jetpack.foodorder.ui.theme.Default50
import com.karolina.jetpack.foodorder.ui.theme.Green800
import com.karolina.jetpack.foodorder.ui.theme.Neutral900

@Composable
fun MapScreen(
    data: UiState.Map
) {

    var isDetailVisible by remember {
        mutableStateOf(true)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                text = "Your order",
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            OrderMap()
        }
        AnimatedVisibility(
            visible = isDetailVisible,
            enter = slideIn { IntOffset(0, 100) } + fadeIn(),
            exit = slideOut { IntOffset(0, 100) } + fadeOut()
        ) {
            Box(contentAlignment = Alignment.BottomCenter) {
                InfoCard(
                    name = data.name,
                    surname = data.surname,
                    sourceAddress = data.sourceAddress,
                    targetAddress = data.targetAddress
                )
            }
        }

        Box(contentAlignment = Alignment.BottomCenter) {
            OutlinedButton(
                colors = ButtonDefaults.buttonColors(Green800),
                shape = RoundedCornerShape(20),
                onClick = { isDetailVisible = !isDetailVisible }) {
                if (isDetailVisible) Text(text = "Hide details")
                else Text(text = "Show details")
            }
        }
    }
}

@Composable
fun InfoCard(
    name: String = "",
    surname: String = "",
    sourceAddress: String = "",
    targetAddress: String = ""
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = Neutral900
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.size(45.dp),
                    painter = painterResource(id = R.drawable.profile_image_me),
                    contentDescription = null,
                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "$name $surname", fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = "123-456-789", fontWeight = FontWeight.Light, color = Color.White)
                }

                Surface(shape = CircleShape, color = Default50) {
                    IconButton(
                        modifier = Modifier.border(
                            1.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        ),
                        onClick = { }) {
                        Icon(
                            modifier = Modifier.size(25.dp, 25.dp),
                            painter = painterResource(id = R.drawable.ic_phone),
                            contentDescription = null,
                            tint = Green800
                        )
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 30.dp)
                    .offset(y = (-20).dp),
                shape = RoundedCornerShape(12),
                shadowElevation = 1.dp
            ) {
                val placeImage = painterResource(id = R.drawable.ic_address)
                val clockImage = painterResource(id = R.drawable.ic_clock)

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoCardRow(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        image = placeImage,
                        address = sourceAddress
                    )
                    Image(
                        modifier = Modifier
                            .padding(start = 42.dp)
                            .size(30.dp, 70.dp),
                        painter = painterResource(id = R.drawable.ic_line),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                    InfoCardRow(
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                        image = clockImage,
                        address = targetAddress
                    )
                }
            }
        }
    }
}

@Composable
fun InfoCardRow(
    modifier: Modifier = Modifier,
    image: Painter,
    address: String
) {
    Row(modifier = modifier) {
        IconButton(
            modifier = modifier.border(1.dp, Color.LightGray, shape = CircleShape),
            onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(25.dp, 25.dp),
                painter = image,
                contentDescription = null,
                tint = Green800
            )
        }
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = address,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun OrderMap(modifier: Modifier = Modifier) {
    val startPlace = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPlace, 10f)
    }

    Surface(modifier = Modifier.fillMaxSize(), shadowElevation = 1.dp) {
        GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState) {
            Marker(
                state = MarkerState(startPlace),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreen(data = sampleMapData)
}