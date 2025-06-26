package com.example.bookandfriend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookandfriend.presentation.navigation.NavigationFunc
import com.example.bookandfriend.presentation.screens.settings.SettingsVM
import com.example.bookandfriend.presentation.ui.theme.BookAndFriendTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsVM: SettingsVM = hiltViewModel()
            val settings by settingsVM.settings.collectAsState()
            val darkTheme = settings.darkThemeEnabled

            BookAndFriendTheme(darkTheme = darkTheme) {
                NavigationFunc()
            }
        }
    }
}