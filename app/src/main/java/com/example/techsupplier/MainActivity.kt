package com.example.techsupplier

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
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
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
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
            val systemUiController: SystemUiController = rememberSystemUiController()

            systemUiController.isStatusBarVisible = false // Status bar
            systemUiController.isNavigationBarVisible = false // Navigation bar
            systemUiController.isSystemBarsVisible = false
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
                    },


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
fun FilterCard(
    isFilter: MutableState<Boolean>,
    details: MutableState<String>
){
    var isMechanic by remember{ mutableStateOf(false) }
    var isElectro by remember{ mutableStateOf(false) }
    var isAnother by remember{ mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        contentAlignment = Alignment.TopCenter) {
    Card(elevation = CardDefaults.elevatedCardElevation(3.dp), modifier = Modifier.fillMaxWidth(0.7f),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3E3D3)
        )) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "done",
                    modifier = Modifier
                        .scale(1.5f)
                        .padding(end = 8.dp).clickable {
                        if (onlyOneIsTrue(
                                isAnother,
                                isMechanic,
                                isElectro
                            )
                        ) {
                            var category = ""
                            if (isMechanic) category = "Механическое"
                            if (isElectro) category = "Электрическое"
                            if (isAnother) category = "Другое"
                            details.value = category
                        }
                        isFilter.value = false
                    }
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
                androidx.wear.compose.material.Text(
                    Filters.MECHANIC,
                    color = Color.Black,
                    fontSize = 16.sp
                )
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
                androidx.wear.compose.material.Text(
                    Filters.ELECTRO,
                    color = Color.Black,
                    fontSize = 16.sp
                )
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
                androidx.wear.compose.material.Text(
                    Filters.ANOTHER,
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
    }
}
@Composable
fun DetailsList(details: List<Detail>,
                innerPadding: PaddingValues,
                isOwn: Boolean = false){
    val isDetailInfo = remember {
        mutableIntStateOf(-1)
    }
    var filteredDetails: List<Detail>
    val category = remember { mutableStateOf("") }
    val uid = remember { mutableStateOf("") }
    when(isDetailInfo.intValue){
        -1->{
            val isFilter = remember { mutableStateOf(false) }

            Column {
                if (!isFilter.value) {
                    var searchText by remember { mutableStateOf("") }
                    var isSearchActive by remember { mutableStateOf(false) }
                    if (!isOwn) {
                        SearchBar(
                            query = searchText,
                            onQueryChange = { searchText = it },
                            onSearch = { /* Выполнить поиск */ },
                            active = isSearchActive,
                            onActiveChange = { isSearchActive = it },
                            placeholder = { Text("Поиск...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            trailingIcon = {
                                if (searchText.isNotEmpty()) {
                                    IconButton(onClick = { searchText = "" }) {
                                        Icon(Icons.Default.Close, contentDescription = "Очистить")
                                    }
                                }
                            }) {
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(17.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(painter = painterResource(R.drawable.outline_filter_list_24),
                            contentDescription = "filter",
                            modifier = Modifier.scale(1.5f).clickable{
                                isFilter.value = true
                            })
                    }
                    filteredDetails = if (category.value!="") details.filter { it.category == category.value }
                    else details
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding)
                            .navigationBarsPadding(),
                        columns = GridCells.Fixed(2)
                    ) {
                        itemsIndexed(filteredDetails) { index, item ->
                            DetailOne(item, isDetailInfo, index)
                        }
                    }
                } else {FilterCard(isFilter, category)}
            }

        }
        -2 -> Profile(details,
            innerPadding = innerPadding,
            uid
            )
        else -> DetailInfo(
            details[isDetailInfo.intValue],
            isDetailInfo,
            uid,
            isOwn
        )
    }
}
@Composable
fun DetailOne(detail: Detail,
              isDetailInfo: MutableState<Int>,
              index: Int){
    val rand = (0..4).random()
    val listImages = listOf(R.drawable.ssw,
        R.drawable.sats,
        R.drawable.sat3,
        R.drawable.sat4,
        R.drawable.sat6)
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

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    if (active) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Column {
                // Поле поиска
                TextField(
                    value = query,
                    onValueChange = onQueryChange,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearch() })
                )

                // Результаты
                content()
            }
        }
    } else {
        // Неактивный поиск (иконка)
        OutlinedButton(
            onClick = { onActiveChange(true) },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = "Поиск", tint = Color(0xFFA25C3E))
            Spacer(Modifier.width(8.dp))
            Text("Поиск",
                color = Color(0xFFA25C3E)
            )
        }
    }
}