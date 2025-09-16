package com.example.techsupplier

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun DetailCard(company: Company,
               isDetail: MutableState<Boolean>){
    var name by remember{ mutableStateOf("") }
    var price by remember{ mutableStateOf("") }
    var info by remember{ mutableStateOf("") }
    var phone by remember{ mutableStateOf("") }
    val firestore = Firebase.firestore
    Card(elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier.fillMaxWidth(0.7f),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFF9A6E)
        )) {
        Column {
            OutlinedTextField(onValueChange = {
                name = it
            },
                label = { Text("Введите название детали", color = Color.Black)},
                value = name, colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE3E3D3),
                    focusedContainerColor = Color(0xFFE3E3D3)
                ))
            OutlinedTextField(onValueChange = {
                price = it
            },
                label = { Text("Цена", color = Color.Black)},
                value = price, colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE3E3D3),
                    focusedContainerColor = Color(0xFFE3E3D3)
                ))
            OutlinedTextField(onValueChange = {
                info = it
            },
                label = { Text("Дополнительная информация", color = Color.Black)},
                value = info, colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE3E3D3),
                    focusedContainerColor = Color(0xFFE3E3D3)
                ))
            OutlinedTextField(onValueChange = {
                phone = it
            },
                label = { Text("Номер телефона", color = Color.Black)},
                value = phone, colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE3E3D3),
                    focusedContainerColor = Color(0xFFE3E3D3)
                ))
            Button(onClick = {
                val run = runCatching {
                    firestore.collection(Paths.DETAILS)
                        .document("${company.uid} ${System.currentTimeMillis()}").set(
                        Detail(
                            name, price.toInt(), listOf(""), info, phone, company
                        )
                    )
                }
                run.onFailure {
                    price = "Неправильный формат данных"
                }
                run.onSuccess {
                    isDetail.value = false
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RectangleShape, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFF9A6E)
                )) {
                Text("Добавить деталь")
            }
        }
    }
}