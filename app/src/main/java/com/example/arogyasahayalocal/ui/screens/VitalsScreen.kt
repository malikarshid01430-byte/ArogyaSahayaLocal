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
import com.example.arogyasahayalocal.data.local.entities.Vital
import com.example.arogyasahayalocal.repository.HealthRepository
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsScreen(navController: NavController, patientId: Int) {
    val context = LocalContext.current
    val repository = remember { HealthRepository(AppDatabase.getDatabase(context).healthDao()) }
    val vitals by repository.getRecentVitals(patientId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Vitals Tracking") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Add Vital Dialog */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Vital")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            // Placeholder for Chart
            Card(modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp)) {
                Box(contentAlignment = androidx.compose.ui.Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("7-Day Trend Chart Placeholder")
                }
            }
            
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(vitals) { vital ->
                    ListItem(
                        headlineContent = { Text("BP: ${vital.systolicBP}/${vital.diastolicBP}, Sugar: ${vital.bloodSugar}") },
                        supportingContent = { Text("Heart Rate: ${vital.heartRate}, Date: ${Date(vital.date)}") }
                    )
                    Divider()
                }
            }
        }
    }
}
