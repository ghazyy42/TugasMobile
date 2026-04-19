package com.example.tugassederhana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                // Background utama yang sangat netral (Off-white)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFAFAFA)
                ) {
                    TodoListApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListApp() {
    // Menyimpan input teks sementara
    var textInput by remember { mutableStateOf("") }

    // Menyimpan daftar tugas yang bisa bertambah/berkurang secara dinamis
    val taskList = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Catatan Tugas", fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFFAFAFA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // --- AREA INPUT & TOMBOL ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Masukkan tugas baru...", fontFamily = FontFamily.SansSerif) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFD32F2F), // Aksen Merah saat diklik
                        cursorColor = Color(0xFFD32F2F)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (textInput.isNotBlank()) {
                            taskList.add(textInput) // Menambah data ke list
                            textInput = ""          // Mengosongkan kolom teks setelah ditambah
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)), // Aksen Merah
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(56.dp) // Menyamakan tinggi tombol dengan kolom input
                ) {
                    Text("Tambah", fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- AREA DAFTAR TUGAS ---
            if (taskList.isEmpty()) {
                // Tampilan kosong jika belum ada data
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Belum ada tugas. Yeay!",
                        color = Color.Gray,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            } else {
                // Tampilan List (bisa di-scroll)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(taskList) { task ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = task,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 16.sp
                                )
                                // Tombol Hapus dengan icon tong sampah
                                IconButton(
                                    onClick = { taskList.remove(task) } // Menghapus data dari list
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Hapus Tugas",
                                        tint = Color(0xFFD32F2F) // Aksen Merah
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}