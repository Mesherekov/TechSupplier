package com.example.techsupplier

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import com.example.techsupplier.ui.theme.TechSupplierTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TechSupplierTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            items = listOf(
                                BottomNavItem(
                                    "Home",
                                    route = "home",
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    "Profile",
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
                ) {
                    Account()
                    val e = 0
                }
                }
        }
    }
    @Composable
    fun DetailsList(details: List<Detail>){
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)) {
            itemsIndexed(details){ _, item ->
                DetailOne(item)
            }
        }
    }
}
@Composable
fun DetailOne(detail: Detail){
    Card(elevation = CardDefaults.elevatedCardElevation(4.dp)) {
        Column {
            AsyncImage(
                model = detail.imageUrl,
                contentDescription = "detail",
                modifier = Modifier
                    .padding(2.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Icon(
                painter = painterResource(R.drawable.baseline_account_balance_wallet_24),
                contentDescription = "wallet"
            )
            Text(detail.company.name,
                fontSize = 10.sp);

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