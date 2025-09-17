package com.example.techsupplier

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun DetailInfo(detail: Detail){
    val rand = (0..2).random()
    val listImages = listOf(R.drawable.gear_wheel1,
        R.drawable.gear_wheel2,
        R.drawable.gear_wheel3)
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)) {
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = listImages[rand],
                contentDescription = "detail",
                modifier = Modifier
                    .padding(2.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        Card(elevation = CardDefaults.elevatedCardElevation(4.dp),
            modifier = Modifier
                .padding(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3E3D3)
            )) {
            Column(Modifier
                .fillMaxWidth()
                .padding(3.dp)) {
                Text("Название: ${detail.name}")
                Text("Цена: ${detail.price}₽")
                Text("Телефон: ${detail.phone}")
                Text("Компания: ${detail.company.name}")
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Информация:")
                    Text(detail.info)
                }

            }
        }
    }
}