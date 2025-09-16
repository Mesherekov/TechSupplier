package com.example.techsupplier

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text


@Composable
fun Account(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Registration()
        }
    }
}


@Composable
fun Registration(){
    var isSignIn by remember {
        mutableStateOf(true)
    }

    Card(elevation = CardDefaults
        .elevatedCardElevation(4.dp), colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F0)
        )) {
        Column(modifier = Modifier.fillMaxWidth(0.7f)) {
            Row{
                Button(modifier = Modifier
                    .weight(1/2f)
                    .padding(1.dp)
                    .clip(RoundedCornerShape(3.dp)), onClick = {
                        isSignIn = true
                },
                    shape = RectangleShape, colors = ButtonDefaults
                        .buttonColors(
                        backgroundColor = if(isSignIn) Color(0xFFFF9A6E) else Color.LightGray
                        )) {
                    Text("Войти")
                }
                Button(modifier = Modifier
                    .weight(1/2f)
                    .padding(1.dp)
                    .clip(RoundedCornerShape(3.dp)), onClick = {
                    isSignIn = false
                },
                    shape = RectangleShape, colors = ButtonDefaults
                        .buttonColors(
                            backgroundColor = if(!isSignIn) Color(0xFFFF9A6E) else Color.LightGray                        )) {
                    Text("Регистрация")
                }
            }
            if(isSignIn)
                SignIN()
             else SignUP()
        }
    }
}

@Composable
fun SignIN(){
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    OutlinedTextField(onValueChange = {
        name = it
    },
        label = { Text("Введите имя", color = Color.Black)},
        value = name, colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFE3E3D3),
            unfocusedContainerColor = Color(0xFFE3E3D3)
        ))
    OutlinedTextField(onValueChange = {
        password = it
    },
        label = { Text("Пароль", color = Color.Black)},
        value = password, colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFE3E3D3),
            unfocusedContainerColor = Color(0xFFE3E3D3)
        ),
        visualTransformation = PasswordVisualTransformation()
    )
    Button(onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        shape = RectangleShape, colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFF9A6E)
        )) {
        Text("Войти")
    }

}
@Composable
fun SignUP(){
    var name by remember { mutableStateOf("") }
    var ip by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }

    OutlinedTextField(onValueChange = {
        name = it
    },
        label = { Text("Введите имя", color = Color.Black)},
        value = name, colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFE3E3D3),
            focusedContainerColor = Color(0xFFE3E3D3)
        ))
    OutlinedTextField(onValueChange = {
        ip = it
    },
        label = { Text("Номер ИП", color = Color.Black)},
        value = ip, colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFE3E3D3), 
            unfocusedContainerColor = Color(0xFFE3E3D3)
        ))
    OutlinedTextField(onValueChange = {
        password = it
    },
        label = { Text("Пароль", color = Color.Black)},
        value = password, colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFE3E3D3),
            unfocusedContainerColor = Color(0xFFE3E3D3)
        ),
        visualTransformation = PasswordVisualTransformation()
    )
    OutlinedTextField(onValueChange = {
        info = it
    },
        label = { Text("Информация о предприятии", color = Color.Black)},
        value = info, colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFE3E3D3),
            unfocusedContainerColor = Color(0xFFE3E3D3)
        )
    )
    Button(onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        shape = RectangleShape, colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFF9A6E)
        )) {
        Text("Создать аккаунт")
    }
}


@Composable
fun Profile(detail: List<Detail>){
    Column(Modifier.fillMaxWidth()) {
        Column(Modifier.border(2.dp,
            color = Color.DarkGray)
            .padding(bottom = 3.dp)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(3.dp), contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "profile",
                    modifier = Modifier.clip(CircleShape).scale(1.5f)
                )
            }
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    "Eeww",
                    color = Color.Black,
                    fontSize = 24.sp
                )
            }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(detail){ _, item ->
                DetailOne(item)
            }
        }
    }
}
