package com.delecrode.devhub.ui.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.delecrode.devhub.navigation.AppDestinations
import com.delecrode.devhub.ui.components.EmailTextField
import com.delecrode.devhub.ui.components.PasswordTextField
import com.delecrode.devhub.ui.components.PrimaryButton
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            viewModel.clearState()
        }
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.navigate(AppDestinations.Home.createRoute(state.userUid ?: "")) {
                popUpTo(AppDestinations.Login.route) { inclusive = true }
            }
        }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Login",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "E-mail",
                    color = MaterialTheme.colorScheme.onBackground,
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
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    fontSize = 14.sp
                )

                PasswordTextField(
                    value = password,
                    onValueChange = { password = it },
                    imeAction = ImeAction.Done,
                    isPasswordVisible = passwordVisible,
                    onVisibilityChange = { passwordVisible = !passwordVisible },
                    //isError = uiState.passwordError != null,
                    //errorMessage = uiState.passwordError ?: ""
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(onClick = { navController.navigate(AppDestinations.ForgotPassword.route) }) {
                        Text("Esqueceu a senha?")
                    }

                    TextButton(onClick = { navController.navigate(AppDestinations.Register.route) }) {
                        Text("Não tem conta? Cadastre-se")
                    }
                }

                PrimaryButton(
                    text = "ENTRAR",
                    onClick = {
                        viewModel.signIn(email, password)
                    },
                    enabled = email.isNotBlank() && password.isNotBlank()
                )
            }
        }
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(0.5f)), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController(), koinViewModel())

}
