package com.example.arogyasahayalocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.arogyasahayalocal.ui.Screen
import com.example.arogyasahayalocal.ui.theme.ArogyaSahayaLocalTheme
import com.example.arogyasahayalocal.viewmodel.MainViewModel
import com.example.arogyasahayalocal.utils.LocaleHelper
import com.example.arogyasahayalocal.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val language by mainViewModel.language.collectAsState()
            val isLoggedIn by mainViewModel.isLoggedIn.collectAsState()

            // Handle Language
            val context = LocalContext.current
            LaunchedEffect(language) {
                LocaleHelper.setLocale(context, language)
            }

            ArogyaSahayaLocalTheme {
                val navController = rememberNavController()
                
                Scaffold(
                    bottomBar = {
                        if (isLoggedIn) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (isLoggedIn) Screen.Dashboard.route else Screen.Login.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Login.route) {
                            LoginScreen(navController, mainViewModel)
                        }
                        composable(Screen.Register.route) {
                            RegisterScreen(navController)
                        }
                        composable(Screen.Dashboard.route) {
                            DashboardScreen(navController)
                        }
                        composable(Screen.Patients.route) {
                            PatientsScreen(navController)
                        }
                        composable(Screen.PatientDetail.route, 
                            arguments = listOf(navArgument("patientId") { type = NavType.IntType })) {
                            val patientId = it.arguments?.getInt("patientId") ?: -1
                            PatientDetailScreen(navController, patientId)
                        }
                        composable(Screen.Records.route,
                            arguments = listOf(navArgument("patientId") { type = NavType.IntType })) {
                            val patientId = it.arguments?.getInt("patientId") ?: -1
                            RecordsScreen(navController, patientId)
                        }
                        composable(Screen.Medicines.route,
                            arguments = listOf(navArgument("patientId") { type = NavType.IntType })) {
                            val patientId = it.arguments?.getInt("patientId") ?: -1
                            MedicinesScreen(navController, patientId)
                        }
                        composable(Screen.Vitals.route,
                            arguments = listOf(navArgument("patientId") { type = NavType.IntType })) {
                            val patientId = it.arguments?.getInt("patientId") ?: -1
                            VitalsScreen(navController, patientId)
                        }
                        composable(Screen.Doctors.route) {
                            DoctorsScreen(navController)
                        }
                        composable(Screen.Staff.route) {
                            StaffScreen(navController)
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen(navController, mainViewModel)
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(navController)
                        }
                        composable(Screen.MedicalReports.route) {
                            MedicalReportsScreen(navController)
                        }
                        composable(Screen.Meetups.route) {
                            MeetupsScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavHostController) {
    val items = listOf(
        Triple(Screen.Dashboard, Icons.Default.Home, "Home"),
        Triple(Screen.Patients, Icons.Default.People, "Patients"),
        Triple(Screen.Doctors, Icons.Default.MedicalServices, "Doctors"),
        Triple(Screen.Settings, Icons.Default.Settings, "Settings")
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        items.forEach { (screen, icon, label) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = currentRoute?.startsWith(screen.route.split("/")[0]) == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
