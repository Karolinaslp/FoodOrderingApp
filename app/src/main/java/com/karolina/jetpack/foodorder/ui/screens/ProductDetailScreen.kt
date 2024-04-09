package com.karolina.jetpack.foodorder.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karolina.jetpack.foodorder.R
import com.karolina.jetpack.foodorder.data.ItemDetail
import com.karolina.jetpack.foodorder.data.UiState
import com.karolina.jetpack.foodorder.data.samples.sampleItemDetailScreen
import com.karolina.jetpack.foodorder.ui.theme.Default50
import com.karolina.jetpack.foodorder.ui.theme.Green700
import com.karolina.jetpack.foodorder.ui.theme.Green800
import com.karolina.jetpack.foodorder.ui.theme.Neutral100

@Composable
fun ProductDetailScreen(
    data: UiState.ItemDetailScreen,
    onItemAdd: (ItemDetail) -> Unit = {},
    onGoToShoppingBag: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        ProductHeader()
        ProductImage(image = data.item.image)
        ProductDetail(item = data.item, alreadyAdded = data.alreadyAdded)
    }
}

@Composable
fun ProductHashTag(name: String) {
    Surface(
        modifier = Modifier.padding(5.dp),
        tonalElevation = 1.dp,
        shape = RoundedCornerShape(10),
        color = Default50,
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = name,
            color = Green700,
        )
    }
}

@Composable
fun ProductDetail(
    item: ItemDetail,
    alreadyAdded: Boolean = false,
    onItemAdd: (ItemDetail) -> Unit = {},
    onGoToShoppingBag: () -> Unit = {}
) {
    var isIngredientsExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var isCaloriesTableExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(10),
    ) {
        Column {
            LazyRow {
                items(items = item.hashTags) { tag ->
                    ProductHashTag(name = tag)
                }
            }
            Row(modifier = Modifier.padding(top = 20.dp)) {
                Text(
                    modifier = Modifier.weight(1f),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    text = item.name
                )
                Text(
                    modifier = Modifier.weight(1f),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = item.price.toString(),
                    textAlign = TextAlign.Center
                )
            }
            Column {
                Row(modifier = Modifier
                    .clickable { isIngredientsExpanded = !isIngredientsExpanded }
                    .padding(top = 45.dp)) {
                    val ingredientsArrow = when (isIngredientsExpanded) {
                        true -> painterResource(id = R.drawable.ic_arrow_down)
                        false -> painterResource(id = R.drawable.ic_arrow_up)
                    }

                    Text(text = "Ingredients", color = Color.Gray)
                    Icon(painter = ingredientsArrow, contentDescription = null)
                }
                if (isIngredientsExpanded) {
                    Text(
                        modifier = Modifier.padding(top = 15.dp),
                        text = item.ingredients,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column {
                Row(modifier = Modifier
                    .clickable { isCaloriesTableExpanded = !isCaloriesTableExpanded }
                    .padding(top = 45.dp)) {
                    val caloriesArrow = when (isCaloriesTableExpanded) {
                        true -> painterResource(id = R.drawable.ic_arrow_down)
                        false -> painterResource(id = R.drawable.ic_arrow_up)
                    }

                    Text(text = "Nutrition values", color = Color.Gray)
                    Icon(painter = caloriesArrow, contentDescription = null)
                }
                if (isCaloriesTableExpanded) {
                    Text(
                        modifier = Modifier.padding(top = 15.dp),
                        text = item.calories,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            ShoppingBagButton(
                alreadyAdded = alreadyAdded,
                onClick = { onItemAdd(item) },
                onGoToShoppingBag = onGoToShoppingBag
            )
        }
    }
}

@Composable
fun ShoppingBagButton(
    alreadyAdded: Boolean,
    onClick: () -> Unit = {},
    onGoToShoppingBag: () -> Unit = {}
) {

    val defaultModifier = Modifier
        .padding(vertical = 16.dp)
        .height(48.dp)
        .fillMaxWidth()

    when (alreadyAdded) {
        true -> {
            OutlinedButton(
                modifier = defaultModifier,
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(Green800)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            modifier = Modifier.size(24.dp, 24.dp),
                            painter = painterResource(id = R.drawable.ic_already_added),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Added",
                            color = Color.White
                        )
                    }
                }
            }
        }

        false -> {
            OutlinedButton(
                modifier = defaultModifier,
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(Green800)
            ) {
                Text(text = "Add to cart", color = Color.White, fontSize = 18.sp)
            }
        }

    }

}

@Composable
fun ProductImage(image: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .size(350.dp, 350.dp)
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = image),
            contentDescription = null,
            alignment = Alignment.Center
        )
    }
}

@Composable
fun ProductHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.ic_arrow_left
            ),
            contentDescription = null
        )
        Icon(
            modifier = Modifier
                .size(35.dp, 35.dp)
                .clip(CircleShape)
                .background(Neutral100),
            painter = painterResource(id = R.drawable.ic_favourite_border),
            contentDescription = null
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(sampleItemDetailScreen)
}