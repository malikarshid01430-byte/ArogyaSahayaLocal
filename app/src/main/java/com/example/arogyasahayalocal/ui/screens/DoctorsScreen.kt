package com.example.arogyasahayalocal.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.arogyasahayalocal.data.local.AppDatabase
import com.example.arogyasahayalocal.data.local.entities.Doctor
import com.example.arogyasahayalocal.repository.DirectoryRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorsScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { DirectoryRepository(AppDatabase.getDatabase(context).directoryDao()) }
    val doctors by repository.allDoctors.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Our Specialist Doctors", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize().background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(doctors) { doctor ->
                DoctorCard(doctor, onCall = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${doctor.contactNumber}"))
                    context.startActivity(intent)
                })
            }
            
            if (doctors.isEmpty()) {
                items(5) { i ->
                    DoctorCard(
                        Doctor(
                            id = i, 
                            name = "Dr. Shanti Swaroop $i", 
                            specialization = "Cardiologist", 
                            contactNumber = "9876543210", 
                            location = "General Hospital, Block A",
                            ranking = 4.5,
                            post = "Senior Surgeon"
                        ),
                        onCall = {}
                    )
                }
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor, onCall: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(60.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(32.dp), tint = MaterialTheme.colorScheme.primary)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(doctor.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(color = Color(0xFFFFC107), shape = RoundedCornerShape(4.dp)) {
                        Row(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.White)
                            Text(doctor.ranking.toString(), style = MaterialTheme.typography.labelSmall, color = Color.White)
                        }
                    }
                }
                Text("${doctor.post} | ${doctor.specialization}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                    Text(doctor.location, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Text("Ph: ${doctor.contactNumber}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            
            IconButton(
                onClick = onCall,
                colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFFE8F5E9), contentColor = Color(0xFF2E7D32))
            ) {
                Icon(Icons.Default.Call, contentDescription = "Call")
            }
        }
    }
}
