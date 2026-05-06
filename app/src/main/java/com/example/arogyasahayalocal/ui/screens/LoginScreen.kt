package com.example.arogyasahayalocal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.arogyasahayalocal.data.local.AppDatabase
import com.example.arogyasahayalocal.repository.UserRepository
import com.example.arogyasahayalocal.viewmodel.*
import com.example.arogyasahayalocal.ui.Screen

@Composable
fun LoginScreen(navController: NavController, mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val repository = remember { UserRepository(AppDatabase.getDatabase(context).userDao()) }
    val authViewModel = remember { AuthViewModel(repository) }
    
    val loginStatus by authViewModel.loginStatus.collectAsState()
    
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(loginStatus) {
        if (loginStatus is AuthResult.Success) {
            mainViewModel.setLoggedIn(true, (loginStatus as AuthResult.Success).user.id)
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Arogya Sahaya Local", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { authViewModel.login(username, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        
        TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
            Text("Don't have an account? Register")
        }
        
        if (loginStatus is AuthResult.Error) {
            Text((loginStatus as AuthResult.Error).message, color = MaterialTheme.colorScheme.error)
        }
    }
}
