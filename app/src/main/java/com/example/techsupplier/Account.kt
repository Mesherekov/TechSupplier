package com.example.techsupplier

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


@Composable
fun Account(){

    val currentUser = Firebase.auth.currentUser
    val isSuccess = remember {
        mutableStateOf(currentUser!=null)
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            if (!isSuccess.value) Registration(isSuccess)
            else Profile(emptyList(),  isSuccess)
        }
    }
}


@Composable
fun Registration(isSuccess: MutableState<Boolean>) {
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
                            backgroundColor = if(!isSignIn) Color(0xFFFF9A6E) else Color.LightGray)) {
                    Text("Регистрация")
                }
            }
            if(isSignIn)
                SignIN(isSuccess)
             else SignUP(isSuccess)
        }
    }
}

@Composable
fun SignIN(isSuccess: MutableState<Boolean>) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var noti by remember { mutableStateOf("Войти") }
    val auth = Firebase.auth
    OutlinedTextField(onValueChange = {
        name = it
    },
        label = { Text("Введите gmail", color = Color.Black)},
        value = name, colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFE3E3D3),
            focusedContainerColor = Color(0xFFE3E3D3)
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
    Button(onClick = {
        auth.signInWithEmailAndPassword(name, password).addOnCompleteListener { task ->
            if (!task.isSuccessful){
                noti = "Неверная почта или пароль"
            } else {isSuccess.value = true}
        }
    },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        shape = RectangleShape, colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFF9A6E)
        )) {
        Text(noti)
    }

}
@Composable
fun SignUP(isSuccess: MutableState<Boolean>) {
    var name by remember { mutableStateOf("") }
    var ip by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }
    var gmail by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var noti by remember { mutableStateOf("Создать аккаунт") }

    val auth = Firebase.auth
    val firestore = Firebase.firestore


    OutlinedTextField(onValueChange = {
        name = it
    },
        label = { Text("Введите имя", color = Color.Black)},
        value = name, colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFE3E3D3),
            focusedContainerColor = Color(0xFFE3E3D3)
        ))
    OutlinedTextField(onValueChange = {
        gmail = it
    },
        label = { Text("Gmail", color = Color.Black)},
        value = gmail, colors = TextFieldDefaults.colors(
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
    Button(onClick = {
        if(name.isNotEmpty() && password.length>=6
            && ip.isNotEmpty() && info.isNotEmpty()
            && gmail.isNotEmpty() && phone.isNotEmpty()){
            auth.createUserWithEmailAndPassword(gmail, password)
                .addOnCompleteListener {task ->
                    noti = if(task.isSuccessful){
                        firestore.collection(Paths.COMPANY)
                            .document(auth.uid.toString()).set(
                            Company(name, info, ip, gmail, phone, auth.uid.toString())
                        )
                        isSuccess.value = true
                        "Аккаунт создан"
                    } else{
                        "Ошибка"
                    }

                }
        }
    },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        shape = RectangleShape, colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFF9A6E)
        )) {
        Text(noti)
    }
}


@Composable
fun Profile(detail: List<Detail>, isSuccess: MutableState<Boolean>){
    val firestore = Firebase.firestore
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val company = remember {mutableStateOf(Company())}
    val isInfo = remember { mutableStateOf(false) }
    val isDetail = remember { mutableStateOf(false) }


    if (currentUser!=null){
        firestore.collection(Paths.COMPANY).document(currentUser.uid).get().addOnCompleteListener { snapshot ->
            company.value = snapshot.result.toObject(Company::class.java) ?: Company(name = "Error")
        }
    }
    if (isInfo.value){
        InfoCompany(company.value, isInfo)
    }else {
        if (isDetail.value){
            DetailCard(company.value, isDetail)
        }else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .statusBarsPadding(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Column(
                        Modifier.border(
                            2.dp,
                            color = Color.DarkGray
                        )
                            .padding(bottom = 3.dp)
                    ) {
                        Spacer(Modifier.height(20.dp))
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(3.dp), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "profile",
                                modifier = Modifier.clip(CircleShape).size(80.dp)
                            )
                        }
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(3.dp), contentAlignment = Alignment.Center
                        ) {
                            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    company.value.name,
                                    color = Color.Black,
                                    fontSize = 24.sp
                                )
                            }
                        }
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(3.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Информация", color = Color.Blue, modifier = Modifier.clickable {
                                isInfo.value = true
                            }, fontSize = 17.sp)
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.outline_logout_24),
                                    contentDescription = "logout",
                                    modifier = Modifier.clickable {
                                        auth.signOut()
                                        isSuccess.value = false
                                    }.scale(2f).padding(10.dp)
                                )
                            }
                        }

                    }

                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        itemsIndexed(detail) { _, item ->
                            DetailOne(item)
                        }
                    }
                }
                Box(
                    Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(), contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            isDetail.value = true
                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFF9A6E)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add", modifier = Modifier.scale(1.5f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoCompany(company: Company, isInfo: MutableState<Boolean>){
    var name by remember { mutableStateOf(company.name) }
    var info by remember { mutableStateOf(company.info) }
    var phone by remember { mutableStateOf(company.phone) }
    val firestore = Firebase.firestore
    val auth = Firebase.auth
    Card(elevation = CardDefaults.elevatedCardElevation(3.dp), modifier = Modifier.fillMaxWidth(0.7f),
         colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFF9A6E)
        )) {
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.padding(2.dp)) {
                Row {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "email")
                    Text(company.gmail, fontSize = 17.sp, color = Color.Black)
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "done", modifier = Modifier.weight(1f).clickable{
                            firestore.collection(Paths.COMPANY)
                                .document(auth.uid.toString())
                                .update("info", info)
                            firestore.collection(Paths.COMPANY)
                                .document(auth.uid.toString())
                                .update("phone", phone)
                            firestore.collection(Paths.COMPANY)
                                .document(auth.uid.toString())
                                .update("name", name)
                            isInfo.value = false
                        }
                    )

                }
                OutlinedTextField(onValueChange = {
                    name = it
                },
                    label = { Text("Имя", color = Color.Black)},
                    value = name, colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE3E3D3),
                        unfocusedContainerColor = Color(0xFFE3E3D3)
                    )
                )
                OutlinedTextField(onValueChange = {
                    phone = it
                },
                    label = { Text("Номер телефона", color = Color.Black)},
                    value = phone, colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE3E3D3),
                        unfocusedContainerColor = Color(0xFFE3E3D3)
                    )
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
            }
        }

    }
}
