package com.example.techsupplier

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Suppress("DEPRECATION")
@Composable
fun DetailInfo(detail: Detail,
               isDetailInfo: MutableIntState,
               uid: MutableState<String>,
               isOwn: Boolean = false){
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
                val annotatedText = buildAnnotatedString {
                    pushStringAnnotation(
                        tag = "URL",
                        annotation = "https://example.com"
                    )
                    withStyle(style = SpanStyle(color = Color.Black, fontSize = 18.sp)){
                        append("Компания: ")
                    }
                    withStyle(style = SpanStyle(color = Color.Blue, fontSize = 18.sp)){
                        append(detail.company.name)
                    }
                }
                ClickableText(text = annotatedText,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = "URL",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let { annotation ->
                            if (!isOwn) {
                                uid.value = detail.company.uid
                                isDetailInfo.intValue = -2
                            }
                        }
                    })
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Информация:")
                    Text(detail.info)
                }

            }
        }
    }
}

@Composable
fun Profile(detail: List<Detail>,
            innerPadding: PaddingValues,
            uid: MutableState<String>){

    val isInfo = remember { mutableStateOf(false) }
    val isDetail = remember { mutableStateOf(false) }

    val detail = detail.filter { it.company.uid == uid.value }
    if (isInfo.value){
        InfoCompany2(detail.first().company, isInfo)
    }else {
        if (isDetail.value){
            DetailCard(detail.first().company, isDetail)
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
                        Spacer(modifier = Modifier.height(6.dp))
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
                                androidx.wear.compose.material.Text(
                                    detail.first().company.name,
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
                            androidx.wear.compose.material.Text(
                                "Информация",
                                color = Color.Blue,
                                modifier = Modifier.clickable {
                                    isInfo.value = true
                                },
                                fontSize = 17.sp
                            )
                        }

                    }

                    DetailsList(detail, innerPadding, true)
                }
                Box(
                    Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(), contentAlignment = Alignment.Center
                ) {

                }
            }
        }
    }
}
@Composable
fun InfoCompany2(company: Company, isInfo: MutableState<Boolean>){
    var name by remember { mutableStateOf(company.name) }
    var info by remember { mutableStateOf(company.info) }
    var phone by remember { mutableStateOf(company.phone) }
    val firestore = Firebase.firestore
    val auth = Firebase.auth
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Card(
            elevation = CardDefaults.elevatedCardElevation(3.dp),
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3E3D3)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(2.dp)) {
                    Row(modifier = Modifier.padding(3.dp)) {
                        Icon(imageVector = Icons.Default.Email, contentDescription = "email")
                        androidx.wear.compose.material.Text(
                            company.gmail,
                            fontSize = 17.sp,
                            color = Color.Black
                        )
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "done", modifier = Modifier.weight(1f).clickable {
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
                    Row(modifier = Modifier.padding(3.dp)) {
                        Icon(imageVector = Icons.Default.AccountBox, contentDescription = "name")
                        androidx.wear.compose.material.Text(
                            company.name,
                            fontSize = 17.sp,
                            color = Color.Black
                        )
                    }
                    Row(modifier = Modifier.padding(3.dp)) {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = "email")
                        androidx.wear.compose.material.Text(
                            company.phone,
                            fontSize = 17.sp,
                            color = Color.Black
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(3.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "email")
                        androidx.wear.compose.material.Text(
                            company.info,
                            fontSize = 17.sp,
                            color = Color.Black
                        )
                    }
                }
            }

        }
    }
}