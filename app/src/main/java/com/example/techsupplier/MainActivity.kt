package com.example.techsupplier

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import com.example.techsupplier.ui.theme.TechSupplierTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class]
        mainViewModel.getAllDetails()
        val currentUser = Firebase.auth.currentUser
        if (currentUser!=null){
            mainViewModel.getProfile(currentUser.uid)

        }

        setContent {
            val navController = rememberNavController()
            TechSupplierTheme {
                val insetsController = WindowCompat.getInsetsController(window, window.decorView)
                insetsController.apply {
                    hide(WindowInsetsCompat.Type.statusBars())
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            items = listOf(
                                BottomNavItem(
                                    "Главная",
                                    route = "home",
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    "Профиль",
                                    route = "profile",
                                    icon = Icons.Default.Person
                                )
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {innerPadding ->
                    Navigation(navController = navController,
                        mainViewModel.detailsState.collectAsState().value,
                        innerPadding)
                }
                }
        }
    }

}
@Composable
fun DetailsList(details: List<Detail>, innerPadding: PaddingValues){
    val isDetailInfo = remember {
        mutableIntStateOf(-1)
    }
    val uid = remember { mutableStateOf("") }
    when(isDetailInfo.intValue){
        -1->{LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .navigationBarsPadding(),
            columns = GridCells.Fixed(2)
        ) {
            itemsIndexed(details) { index, item ->
                DetailOne(item, isDetailInfo, index)
            }
        }}
        -2 -> Profile(details,
            innerPadding = innerPadding,
            uid
            )
        else -> DetailInfo(
            details[isDetailInfo.intValue],
            isDetailInfo,
            uid
        )
    }
}
@Composable
fun DetailOne(detail: Detail,
              isDetailInfo: MutableState<Int>,
              index: Int){
    val rand = (0..2).random()
    val listImages = listOf(R.drawable.gear_wheel1,
        R.drawable.gear_wheel2,
        R.drawable.gear_wheel3)
    Card(elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .padding(5.dp)
            .clickable{
                isDetailInfo.value = index
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFAD85)
        )) {
        Column(modifier = Modifier.padding(2.dp)) {
               AsyncImage(
                   model = listImages[rand],
                   contentDescription = "detail",
                   modifier = Modifier
                       .padding(2.dp)
                       .clip(RoundedCornerShape(10.dp))
               )

            Row {
                Icon(
                    painter = painterResource(R.drawable.baseline_account_balance_wallet_24),
                    contentDescription = "wallet"
                )
                Text("${detail.price}₽")
            }
            Text(detail.company.name,
                fontSize = 15.sp)

            Text(detail.name)
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
    val badgeCount: Int = 0
)

@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
){
    val backStack = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier,
        containerColor = Color(242f, 221f, 198f),
        tonalElevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route==backStack.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },

                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = item.icon, contentDescription = item.label)

                        Text(
                            text = item.label,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                }
            )
        }
    }
}