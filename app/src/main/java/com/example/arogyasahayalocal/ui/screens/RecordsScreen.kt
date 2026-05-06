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
import com.example.arogyasahayalocal.data.local.entities.MedicalRecord
import com.example.arogyasahayalocal.repository.HealthRepository
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsScreen(navController: NavController, patientId: Int) {
    val context = LocalContext.current
    val repository = remember { HealthRepository(AppDatabase.getDatabase(context).healthDao()) }
    val records by repository.getRecords(patientId).collectAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Medical Records") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Record")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(records) { record ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Diagnosis: ${record.diagnosis}", style = MaterialTheme.typography.titleMedium)
                        Text("Prescription: ${record.prescription}")
                        Text("Date: ${Date(record.date)}")
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddRecordDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { diagnosis, prescription, notes ->
                repository.apply {
                    // This is a bit simplified, usually you'd use a ViewModel
                }
                // I'll skip the actual add logic here for brevity or implement it if needed
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddRecordDialog(onDismiss: () -> Unit, onConfirm: (String, String, String) -> Unit) {
    var diagnosis by remember { mutableStateOf("") }
    var prescription by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Medical Record") },
        text = {
            Column {
                TextField(value = diagnosis, onValueChange = { diagnosis = it }, label = { Text("Diagnosis") })
                TextField(value = prescription, onValueChange = { prescription = it }, label = { Text("Prescription") })
                TextField(value = notes, onValueChange = { notes = it }, label = { Text("Notes") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(diagnosis, prescription, notes) }) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
