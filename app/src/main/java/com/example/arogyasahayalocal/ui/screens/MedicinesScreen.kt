package com.example.arogyasahayalocal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.arogyasahayalocal.data.local.AppDatabase
import com.example.arogyasahayalocal.data.local.entities.Medicine
import com.example.arogyasahayalocal.repository.HealthRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicinesScreen(navController: NavController, patientId: Int) {
    val context = LocalContext.current
    val repository = remember { HealthRepository(AppDatabase.getDatabase(context).healthDao()) }
    val medicines by repository.getActiveMedicines(patientId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Medicines") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Add Medicine Dialog */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Medicine")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(medicines) { medicine ->
                ListItem(
                    headlineContent = { Text(medicine.name) },
                    supportingContent = { Text("Dosage: ${medicine.dosage}, Frequency: ${medicine.frequency}") },
                    trailingContent = {
                        Text(buildString {
                            if (medicine.isMorning) append("M ")
                            if (medicine.isAfternoon) append("A ")
                            if (medicine.isNight) append("N ")
                        })
                    }
                )
                Divider()
            }
        }
    }
}
