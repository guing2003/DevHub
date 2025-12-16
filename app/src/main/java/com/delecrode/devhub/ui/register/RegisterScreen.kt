package com.delecrode.devhub.ui.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.delecrode.devhub.navigation.AppDestinations
import com.delecrode.devhub.ui.components.EmailTextField
import com.delecrode.devhub.ui.components.GenericOutlinedTextField
import com.delecrode.devhub.ui.components.PasswordTextField
import com.delecrode.devhub.ui.components.PrimaryButton
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel) {

    var userName by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmVisible by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            viewModel.clearState()
        }
    }

    LaunchedEffect(state.isSuccess) {
        if(state.isSuccess){
            navController.navigate(AppDestinations.Login.route)
        }
    }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Cadastro",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            "Preencha os campos abaixo para criar sua conta no DevHub " +
                                    "\nO seu nome de usuario deve ser o mesmo do seu usuario do GitHub",
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Nome Completo",
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            fontSize = 14.sp
                        )

                        GenericOutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = "Nome Completo",
                            leadingIcon = Icons.Default.Person,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Nome de Usuario",
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            fontSize = 14.sp
                        )

                        GenericOutlinedTextField(
                            value = userName,
                            onValueChange = { userName = it },
                            label = "Nome de Usuario",
                            leadingIcon = Icons.Default.Person,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "E-mail",
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            fontSize = 14.sp
                        )

                        EmailTextField(
                            value = email,
                            onValueChange = { email = it },
                            imeAction = ImeAction.Next,
                            //isError = uiState.emailError != null,
                            //errorMessage = uiState.emailError ?: ""
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Senha",
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            fontSize = 14.sp
                        )

                        PasswordTextField(
                            value = password,
                            onValueChange = { password = it },
                            imeAction = ImeAction.Next,
                            isPasswordVisible = passwordVisible,
                            onVisibilityChange = { passwordVisible = !passwordVisible },
                            //isError = uiState.passwordError != null,
                            //errorMessage = uiState.passwordError ?: ""
                        )

                        Spacer(modifier = Modifier.height(16.dp))


                        Text(
                            text = "Confirmar Senha",
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            fontSize = 14.sp
                        )

                        PasswordTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            imeAction = ImeAction.Done,
                            isPasswordVisible = passwordConfirmVisible,
                            onVisibilityChange = {
                                passwordConfirmVisible = !passwordConfirmVisible
                            },
                            label = "Confirmar Senha"
                            //isError = uiState.passwordError != null,
                            //errorMessage = uiState.passwordError ?: ""
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        PrimaryButton(
                            text = "Cadastrar",
                            onClick = {
                                viewModel.signUp(
                                    name = name,
                                    username = userName,
                                    email = email,
                                    password = password
                                )
                            },
                            enabled = email.isNotBlank() && password.isNotBlank() && password == confirmPassword && password.length >= 6
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController(), koinViewModel())
}