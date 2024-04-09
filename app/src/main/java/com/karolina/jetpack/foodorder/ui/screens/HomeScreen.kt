package com.karolina.jetpack.foodorder.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karolina.jetpack.foodorder.R
import com.karolina.jetpack.foodorder.data.ItemDetail
import com.karolina.jetpack.foodorder.data.UiState
import com.karolina.jetpack.foodorder.data.samples.sampleHeader
import com.karolina.jetpack.foodorder.data.samples.sampleHomeData
import com.karolina.jetpack.foodorder.ui.theme.Green800
import com.karolina.jetpack.foodorder.ui.theme.Neutral900

@Composable
fun HomeScreen(
    data: UiState.Home,
    onItemClick: (ItemDetail) -> Unit,
    onProfileClick: () -> Unit,
    onSearch: (String) -> Unit
) {

    val scrollState = rememberScrollState()
    var text by remember {
        mutableStateOf("")
    }
    var selectedCategoryTab by rememberSaveable {
        mutableStateOf("Pizza")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 2.dp, bottom = 10.dp, end = 2.dp)
    ) {
        HomeHeader(address = data.userData.address, onProfileClick = onProfileClick)
        WelcomeText(name = data.userData.name)
        SearchField(text = text, onSearch = {
            text = it
            onSearch(it)
        })
        PromotionAds()
        OfferList(
            headers = sampleHeader,
            products = data.products,
            selectedCategoryTab = selectedCategoryTab,
            onTabClick = {category ->
                         selectedCategoryTab = category
                
            },
            onItemClick = onItemClick
        )
    }
}

@Composable
fun OfferList(
    headers: List<String> = emptyList(),
    selectedCategoryTab: String = "",
    products: List<ItemDetail> = emptyList(),
    onTabClick: (String) -> Unit = {},
    onItemClick: (ItemDetail) -> Unit = {}
) {
    Column() {
        TabHeaders(
            selectedTab = selectedCategoryTab,
            headers = headers,
            onTabClick = onTabClick
        )
        LazyRow {
            items(items = products) { item ->
                val bitmap = ImageBitmap.imageResource(id = item.image)
                OfferItem(bitmap = bitmap, item = item, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun OfferItem(
    bitmap: ImageBitmap,
    item: ItemDetail,
    onItemClick: (ItemDetail) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .clickable { onItemClick(item) },
        tonalElevation = 10.dp,
        shape = RoundedCornerShape(10)
    ) {
        Column {
            Row {
                Image(
                    modifier = Modifier
                        .size(width = 250.dp, height = 150.dp)
                        .offset(25.dp),
                    bitmap = bitmap,
                    contentDescription = null,
                    alignment = Alignment.TopCenter
                )
                Surface(tonalElevation = 16.dp, shape = RoundedCornerShape(10)) {
                    Box(contentAlignment = Alignment.TopCenter) {
                        Image(
                            modifier = Modifier.size(50.dp, 50.dp),
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = null,
                            alignment = Alignment.BottomEnd,
                            colorFilter = ColorFilter.tint(Green800)
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                text = item.name,
                fontWeight = Bold
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 16.dp),
                text = item.price.toString(),
                fontWeight = Bold
            )
        }
    }
}

@Composable
fun TabHeaders(
    selectedTab: String = "Pizza",
    headers: List<String> = emptyList(),
    onTabClick: (String) -> Unit = {}
) {

    LazyRow {
        items(items = headers) { header ->
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                text = header,
                fontWeight = Bold,
                fontSize = 20.sp,
                color = if (selectedTab == header) Color.Black else Color.Gray
            )
        }
    }
}

@Composable
fun PromotionAds() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp),
        color = Neutral900,
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(10)
    ) {
        Row {
            Column(modifier = Modifier.padding(start = 10.dp, top = 16.dp)) {
                Text(
                    text = "-20% discount",
                    fontSize = 22.sp,
                    fontWeight = Bold,
                    color = Color.White
                )
                Text(text = "Vegetarian pizza", color = Color.White)
                IconButton(
                    modifier = Modifier
                        .padding(5.dp)
                        .border(
                            border = BorderStroke(1.dp, Color.LightGray),
                            shape = RoundedCornerShape(10)
                        ),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Image(
                    modifier = Modifier.size(150.dp, 150.dp),
                    painter = painterResource(id = R.drawable.pizza_three),
                    contentDescription = null
                )
            }
        }
    }

}

@Composable
fun SearchField(text: String, onSearch: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 10.dp),
        value = text,
        onValueChange = { onSearch(it) },
        label = { Text(text = "Search") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null
            )
        }
    )
}

@Composable
fun WelcomeText(name: String = "") {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = "Hi $name, \nwhat are you craving for today?",
            fontSize = 22.sp,
            fontWeight = Bold
        )

    }

}

@Composable
fun HomeHeader(
    address: String = "",
    onProfileClick: () -> Unit = {}
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val arrowIcon = when (isExpanded) {
        true -> ImageVector.vectorResource(id = R.drawable.ic_arrow_up)
        false -> ImageVector.vectorResource(id = R.drawable.ic_arrow_down)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            Row(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(text = "Your address", color = Color.Gray)
                Icon(imageVector = arrowIcon, contentDescription = null)
            }
            if (isExpanded) {
                Text(text = address, fontWeight = FontWeight.Bold)
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                modifier = Modifier
                    .clickable { onProfileClick() }
                    .size(48.dp, 48.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.profile_image_me),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeHeaderPreview() {
    HomeHeader(address = "Narutowicza 42/28a")
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        data = sampleHomeData,
        onItemClick = { /*TODO*/ },
        onProfileClick = { /*TODO*/ }) {

    }
}