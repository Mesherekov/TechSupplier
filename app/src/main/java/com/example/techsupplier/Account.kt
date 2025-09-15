package com.example.techsupplier

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.CombinedModifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import java.nio.file.WatchEvent

@Preview(showBackground = true)
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
        .elevatedCardElevation(4.dp)) {
        Column(modifier = Modifier.fillMaxWidth(0.5f)) {
            Row{
                Button(modifier = Modifier
                    .weight(1/2f)
                    .padding(1.dp)
                    .clip(RoundedCornerShape(3.dp)), onClick = {
                        isSignIn = true
                },
                    shape = RectangleShape, colors = ButtonDefaults
                        .buttonColors(
                        backgroundColor = if(isSignIn) Color(0xFF8B1DB3) else Color.LightGray
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
                            backgroundColor = if(!isSignIn) Color(0xFF8B1DB3) else Color.LightGray                        )) {
                    Text("Регистрация")
                }
            }
            if(isSignIn){
                SignIn()
            } else SignOn()
        }
    }
}

@Composable
fun SignIn(){
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    OutlinedTextField(onValueChange = {
        name = it
    },
        label = { Text("Введите имя", color = Color.Black)},
        value = name, colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black
        ))
    OutlinedTextField(onValueChange = {
        password = it
    },
        label = { Text("Пароль", color = Color.Black)},
        value = password, colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black
        ),
        visualTransformation = PasswordVisualTransformation()
    )
    Button(onClick = {},
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape) {
        Text("Войти")
    }

}
@Composable
fun SignOn(){

}