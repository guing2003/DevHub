package com.delecrode.devhub.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.delecrode.devhub.R
import com.delecrode.devhub.presentation.navigation.AppDestinations
import com.delecrode.devhub.presentation.components.RepoItemCard
import com.delecrode.devhub.presentation.components.UserProfileHeader
import com.delecrode.devhub.ui.theme.PrimaryBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {

    val uiState = homeViewModel.uiState.collectAsState()
    val userForSearchGit = uiState.value.userForSearchGit
    val userForGit = uiState.value.userForGit
    val userForFirebase = uiState.value.userForFirebase

    val repos = uiState.value.repos

    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(uiState.value.error) {
        if (uiState.value.error != null) {
            Toast.makeText(context, uiState.value.error, Toast.LENGTH_SHORT).show()
            homeViewModel.clearStates()
            homeViewModel.clearUi()
        }
    }
    LaunchedEffect(Unit) {
        homeViewModel.getUserForFirebase()
    }

    DisposableEffect(Unit) {
        onDispose {
            homeViewModel.clearUi()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                        ) {
                            AsyncImage(
                                model = userForGit?.avatar_url ?: R.drawable.ic_person_24,
                                contentDescription = "Foto de Perfil",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = userForFirebase?.fullName ?: "",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = userForFirebase?.username ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Meu Perfil") },
                            onClick = {
                                expanded = false
                                navController.navigate(AppDestinations.Profile.route)
                                homeViewModel.clearStates()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Favoritos") },
                            onClick = {
                                expanded = false
                                navController.navigate(AppDestinations.ReposFav.route)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Sair") },
                            onClick = {
                                expanded = false
                                homeViewModel.signOut()
                                navController.navigate(AppDestinations.Profile.route) {
                                    popUpTo(0)
                                }
                                homeViewModel.clearStates()
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }

    )
    { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.value.searchText,
                        onValueChange = { value ->
                            homeViewModel.onSearchTextChange(value)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        placeholder = { Text("Digite o nome do usuario") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = Color.Gray
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                homeViewModel.onSearchClick()
                                keyboardController?.hide()
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_search_24),
                                    contentDescription = "Pesquisar",
                                    tint = PrimaryBlue
                                )
                            }
                        },
                        leadingIcon = {
                            if (uiState.value.searchText.isNotBlank()) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            homeViewModel.clearUi()
                                            keyboardController?.hide()
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_close_24),
                                        contentDescription = "Limpar",
                                        tint = PrimaryBlue,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                keyboardController?.hide()
                                homeViewModel.onSearchClick()
                            }
                        ),
                    )
                }
            }


            userForSearchGit.let { user ->
                item {
                    Column(
                        modifier = Modifier
                            .padding(8.dp),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        UserProfileHeader(
                            user?.avatar_url,
                            user?.name ?: "",
                            user?.login ?: "",
                            user?.bio ?: ""
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (user?.repos_url != null) {
                            Text(
                                "Repositórios", fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                    }
                }
            }
            items(repos) { repo ->
                RepoItemCard(
                    repo = repo,
                    navController = navController,
                    login = userForGit?.login ?: ""
                )
            }
        }
    }
    if (uiState.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .zIndex(11f)
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController(), viewModel())
}
