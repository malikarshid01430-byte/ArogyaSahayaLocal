package com.example.arogyasahayalocal.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.arogyasahayalocal.data.local.AppDatabase
import com.example.arogyasahayalocal.data.local.entities.Patient
import com.example.arogyasahayalocal.repository.PatientRepository
import com.example.arogyasahayalocal.viewmodel.PatientViewModel
import com.example.arogyasahayalocal.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientsScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { PatientRepository(AppDatabase.getDatabase(context).patientDao()) }
    val viewModel = remember { PatientViewModel(repository) }
    val patients by viewModel.patients.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Patients") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Patient")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(patients) { patient ->
                ListItem(
                    headlineContent = { Text(patient.name) },
                    supportingContent = { Text("Age: ${patient.age}, ${patient.gender}") },
                    trailingContent = {
                        IconButton(onClick = { viewModel.deletePatient(patient) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    },
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.PatientDetail.createRoute(patient.id))
                    }
                )
                Divider()
            }
        }
    }

    if (showAddDialog) {
        AddPatientDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, age, gender, contact, address, conditions ->
                viewModel.addPatient(Patient(
                    name = name, age = age.toIntOrNull() ?: 0, gender = gender,
                    contact = contact, address = address, chronicConditions = conditions
                ))
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddPatientDialog(onDismiss: () -> Unit, onConfirm: (String, String, String, String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var conditions by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Patient") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = age, onValueChange = { age = it }, label = { Text("Age") })
                TextField(value = gender, onValueChange = { gender = it }, label = { Text("Gender") })
                TextField(value = contact, onValueChange = { contact = it }, label = { Text("Contact") })
                TextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
                TextField(value = conditions, onValueChange = { conditions = it }, label = { Text("Chronic Conditions") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, age, gender, contact, address, conditions) }) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
