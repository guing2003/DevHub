package com.delecrode.devhub.ui.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.delecrode.devhub.navigation.AppDestinations
import com.delecrode.devhub.ui.components.RepoItemCard
import com.delecrode.devhub.ui.components.UserProfileHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {

    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val state = viewModel.uiState.collectAsState()

    val user = state.value.userForGit
    val repos = state.value.repos

    LaunchedEffect(Unit) {
        viewModel.getUserForFirebase()
    }

    LaunchedEffect(state.value.error) {
        if (state.value.error != null) {
            Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
            viewModel.clearState()
        }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Meu Perfil") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
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
                                viewModel.signOut()
                                navController.navigate(AppDestinations.Login.route) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                }
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
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    horizontalAlignment = CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    UserProfileHeader(
                        avatar_url = user?.avatar_url,
                        name = user?.name ?: "",
                        login = user?.login ?: "",
                        bio = user?.bio ?: ""
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (user?.repos_url != null) {
                        Text(
                            "Repositórios", fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(repos) { repo ->
                RepoItemCard(
                    repo = repo,
                    navController = navController,
                    login = user?.login ?: ""
                )
            }
        }
    }
    if (state.value.isLoading) {
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

