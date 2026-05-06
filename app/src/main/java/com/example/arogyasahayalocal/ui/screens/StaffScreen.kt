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
import com.example.arogyasahayalocal.data.local.entities.Staff
import com.example.arogyasahayalocal.repository.DirectoryRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { DirectoryRepository(AppDatabase.getDatabase(context).directoryDao()) }
    val staffList by repository.allStaff.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nurses & ASHA Workers", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize().background(Color(0xFFF0F4F8)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(staffList) { staff ->
                StaffCard(staff, onCall = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${staff.contactNumber}"))
                    context.startActivity(intent)
                })
            }
            
            if (staffList.isEmpty()) {
                items(5) { i ->
                    StaffCard(
                        Staff(
                            id = i, 
                            name = "Sunita Devi $i", 
                            role = if (i%2==0) "ASHA Worker" else "Nurse", 
                            contactNumber = "8877665544",
                            ranking = 4.2,
                            post = if (i%2==0) "Community Health Lead" else "Nursing Officer"
                        ),
                        onCall = {}
                    )
                }
            }
        }
    }
}

@Composable
fun StaffCard(staff: Staff, onCall: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(50.dp).clip(CircleShape).background(if (staff.role == "Nurse") Color(0xFFE0F7FA) else Color(0xFFFFF3E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (staff.role == "Nurse") Icons.Default.MedicalServices else Icons.Default.Handshake,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (staff.role == "Nurse") Color(0xFF00796B) else Color(0xFFE65100)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(staff.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(color = Color(0xFF4CAF50), shape = RoundedCornerShape(4.dp)) {
                        Row(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(10.dp), tint = Color.White)
                            Text(staff.ranking.toString(), style = MaterialTheme.typography.labelSmall, color = Color.White)
                        }
                    }
                }
                Text("${staff.post} | ${staff.role}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Text("Ph: ${staff.contactNumber}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
            }
            
            IconButton(
                onClick = onCall,
                colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFFE3F2FD), contentColor = Color(0xFF1976D2))
            ) {
                Icon(Icons.Default.Call, contentDescription = "Call")
            }
        }
    }
}
