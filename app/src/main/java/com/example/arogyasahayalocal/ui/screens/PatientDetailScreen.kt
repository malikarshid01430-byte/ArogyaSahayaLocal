package com.example.arogyasahayalocal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.arogyasahayalocal.data.local.AppDatabase
import com.example.arogyasahayalocal.data.local.entities.Patient
import com.example.arogyasahayalocal.repository.PatientRepository
import com.example.arogyasahayalocal.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDetailScreen(navController: NavController, patientId: Int) {
    val context = LocalContext.current
    val repository = remember { PatientRepository(AppDatabase.getDatabase(context).patientDao()) }
    var patient by remember { mutableStateOf<Patient?>(null) }

    LaunchedEffect(patientId) {
        patient = repository.getPatientById(patientId)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(patient?.name ?: "Patient Details") }) }
    ) { padding ->
        patient?.let { p ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                Text("Age: ${p.age}", style = MaterialTheme.typography.bodyLarge)
                Text("Gender: ${p.gender}", style = MaterialTheme.typography.bodyLarge)
                Text("Contact: ${p.contact}", style = MaterialTheme.typography.bodyLarge)
                Text("Address: ${p.address}", style = MaterialTheme.typography.bodyLarge)
                Text("Conditions: ${p.chronicConditions}", style = MaterialTheme.typography.bodyLarge)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(onClick = { navController.navigate(Screen.Records.createRoute(p.id)) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Medical Records")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { navController.navigate(Screen.Medicines.createRoute(p.id)) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Medicines")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { navController.navigate(Screen.Vitals.createRoute(p.id)) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Vitals Tracking")
                }
            }
        }
    }
}
