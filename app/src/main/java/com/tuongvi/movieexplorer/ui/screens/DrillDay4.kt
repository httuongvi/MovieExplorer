package com.tuongvi.movieexplorer

import android.view.Surface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import java.nio.file.WatchEvent


data class Food(
    val id: Int,
    val name: String,
    val price: String,
    val imagePath: String
)

data class  ChipCategory(
    val id: Int,
    val name: String
)

@Composable
fun FoodCard(
    food: Food,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier.fillMaxWidth().padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://picsum.photos${food.imagePath}",
                contentDescription = "Ảnh đồ ăn",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_loading),
                error = painterResource(R.drawable.ic_error)
            )
            Text(
                text = food.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = food.price,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(end = 4.dp)
            )

        }
    }
}

@Composable
fun ChipView(){
    val sampleChip = listOf(
        ChipCategory(1, "Burger"),
        ChipCategory(2, "Salad"),
        ChipCategory(3, "Pizza"),
        ChipCategory(4, "Chicken"),
        ChipCategory(5, "Cake"),
        ChipCategory(6, "Candy"),
        ChipCategory(7, "Ice Cream"),
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = sampleChip,
            key = {chip -> chip.id}
        ){chip ->
            AssistChip(
                onClick = {},
                label = {Text(chip.name)}
            )

        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListFoodScreen(){
    val sampleFoods = listOf(
        Food(
            id = 1,
            name = "Cheese Burger",
            price = "$8.99",
            imagePath = "/100"
        ),
        Food(
            id = 2,
            name = "Pepperoni Pizza",
            price = "$12.50",
            imagePath = "/200"
        ),
        Food(
            id = 3,
            name = "Sushi Set",
            price = "$15.99",
            imagePath = "/300"
        ),
        Food(
            id = 4,
            name = "Grilled Steak",
            price = "$24.90",
            imagePath = "/400"
        ),
        Food(
            id = 5,
            name = "Fried Chicken",
            price = "$10.99",
            imagePath = "/500"
        ),
        Food(
            id = 6,
            name = "Spaghetti Carbonara",
            price = "$13.50",
            imagePath = "/600"
        ),
        Food(
            id = 7,
            name = "Salmon Salad",
            price = "$11.75",
            imagePath = "/200/300"
        ),
        Food(
            id = 8,
            name = "Chocolate Cake",
            price = "$6.50",
            imagePath = "/700"
        ),
        Food(
            id = 9,
            name = "Ice Cream",
            price = "$4.99",
            imagePath = "/800"
        ),
        Food(
            id = 10,
            name = "Bubble Milk Tea",
            price = "$5.99",
            imagePath = "/900"
        )
    )

    Surface(
        modifier = Modifier.fillMaxWidth()

    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        ) {
            ChipView()

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = sampleFoods,
                    key = {food -> food.id}
                ){food ->
                    FoodCard(food, onClick = {})
                }
            }
        }

    }
}
