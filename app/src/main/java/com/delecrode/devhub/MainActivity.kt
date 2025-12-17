package com.delecrode.devhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.delecrode.devhub.domain.session.SessionViewModel
import com.delecrode.devhub.presentation.navigation.AppNavHost
import com.delecrode.devhub.presentation.ui.theme.DevHubTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DevHubTheme {
                val sessionViewModel : SessionViewModel = koinViewModel()
                AppNavHost(sessionViewModel)
            }
        }
    }
}
