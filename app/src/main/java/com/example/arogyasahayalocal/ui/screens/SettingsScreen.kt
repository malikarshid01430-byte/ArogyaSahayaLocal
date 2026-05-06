package com.example.arogyasahayalocal.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.arogyasahayalocal.viewmodel.MainViewModel
import com.example.arogyasahayalocal.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, mainViewModel: MainViewModel) {
    val languages = listOf(
        "English" to "en",
        "Hindi" to "hi",
        "Kannada" to "kn",
        "Tamil" to "ta",
        "Telugu" to "te",
        "Malayalam" to "ml",
        "Marathi" to "mr",
        "Bengali" to "bn",
        "Gujarati" to "gu",
        "Punjabi" to "pa"
    )

    var showLanguageDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            ListItem(
                headlineContent = { Text("Profile") },
                supportingContent = { Text("Update your personal information") },
                modifier = Modifier.clickable { navController.navigate(Screen.Profile.route) }
            )
            Divider()
            ListItem(
                headlineContent = { Text("Language") },
                supportingContent = { Text("Change app language") },
                modifier = Modifier.clickable { showLanguageDialog = true }
            )
            Divider()
            ListItem(
                headlineContent = { Text("Logout") },
                modifier = Modifier.clickable {
                    mainViewModel.setLoggedIn(false)
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text("Select Language") },
            text = {
                LazyColumn {
                    items(languages) { (name, code) ->
                        Text(
                            text = name,
                            modifier = Modifier.fillMaxWidth().clickable {
                                mainViewModel.setLanguage(code)
                                showLanguageDialog = false
                            }.padding(16.dp)
                        )
                    }
                }
            },
            confirmButton = {}
        )
    }
}
