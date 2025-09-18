package com.example.techsupplier

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Checkbox
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
    var isMechanic by remember{ mutableStateOf(false) }
    var isElectro by remember{ mutableStateOf(false) }
    var isAnother by remember{ mutableStateOf(false) }

    var isError by remember{ mutableStateOf("Добавить деталь") }


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
            Card(elevation = CardDefaults.elevatedCardElevation(3.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE3E3D3)
                ),
                modifier = Modifier.padding(4.dp)) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp),
                    contentAlignment = Alignment.Center) {
                    Text(
                        "Категория изделия",
                        color = Color.Black,
                        fontSize = 17.sp
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .toggleable(
                            value = isMechanic,
                            onValueChange = { isMechanic = !isMechanic },
                            role = Role.Checkbox
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isMechanic,
                        onCheckedChange = null, colors = CheckboxDefaults.colors(
                            uncheckedColor = Color.DarkGray,
                            disabledUncheckedColor = Color.Black,
                            checkedColor = Color(0xFFE3E3D3),
                            checkmarkColor = Color.Black
                        )
                    )
                    Text("Механическое",
                        color = Color.Black,
                        fontSize = 16.sp)
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .toggleable(
                            value = isElectro,
                            onValueChange = { isElectro = !isElectro },
                            role = Role.Checkbox
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isElectro,
                        onCheckedChange = null, colors = CheckboxDefaults.colors(
                            uncheckedColor = Color.DarkGray,
                            disabledUncheckedColor = Color.Black,
                            checkedColor = Color(0xFFE3E3D3),
                            checkmarkColor = Color.Black
                        )
                    )
                    Text("Электронное",
                        color = Color.Black,
                        fontSize = 16.sp)
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .toggleable(
                            value = isAnother,
                            onValueChange = { isAnother = !isAnother },
                            role = Role.Checkbox
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isAnother,
                        onCheckedChange = null, colors = CheckboxDefaults.colors(
                            uncheckedColor = Color.DarkGray,
                            disabledUncheckedColor = Color.Black,
                            checkedColor = Color(0xFFE3E3D3),
                            checkmarkColor = Color.Black
                        )
                    )
                    Text("Другое",
                        color = Color.Black,
                        fontSize = 16.sp)
                }
            }
            Button(onClick = {
                val run = runCatching {
                    if (name.isNotEmpty() && price.isNotEmpty() && info.isNotEmpty() ) {
                        var category = ""
                        if (isMechanic) category = "Механическое"
                        if (isElectro) category = "Электрическое"
                        if (isAnother) category = "Другое"

                        firestore.collection(Paths.DETAILS)
                            .document("${company.uid} ${System.currentTimeMillis()}").set(
                                Detail(
                                    name, price.toInt(), category, info, phone, company
                                )
                            )
                    }
                }
                run.onFailure {
                    isError = "Неправильный формат данных\n или выбрано более одной категории"
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
                Text(isError)
            }
        }
    }
}
fun onlyOneIsTrue(vararg booleans: Boolean): Boolean {
    return booleans.count { it } == 1
}