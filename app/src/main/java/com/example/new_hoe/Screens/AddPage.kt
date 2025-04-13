package com.example.new_hoe.Screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.new_hoe.Supabase.uploadToSupabase
import kotlinx.coroutines.launch

@Composable
fun AddPage() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") } // Changed from discription
    var cost by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var isUploading by remember { mutableStateOf(false) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { imagePicker.launch("image/*") }) {
            Text("Pick Image")
        }

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Selected Image",
                modifier = Modifier.size(150.dp)
            )
        }

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cost,
            onValueChange = {
                if (it.matches(Regex("^\\d*\\.?\\d{0,2}$")) || it.isEmpty()) {
                    cost = it
                }
            },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (imageUri != null && description.isNotBlank() && cost.isNotBlank()) {
                    isUploading = true
                    scope.launch {
                        val result = uploadToSupabase(context, description, cost,imageUri!!)
                        isUploading = false
                        status = if (result.isSuccess) {
                            imageUri = null
                            description = ""
                            cost = ""
                            "Upload successful!"
                        } else {
                            val error = result.exceptionOrNull()?.message
                            when {
                                error?.contains("Could not open URI stream") == true -> "Failed to read image"
                                error?.contains("Failed to read image") == true -> "Invalid image file"
                                error?.contains("File is empty") == true -> "Selected image is empty"
                                error?.contains("Image too large") == true -> "Image too large (max 5MB)"
                                error?.contains("Storage upload failed") == true -> "Failed to upload image to server"
                                error?.contains("Database insert failed") == true -> "Failed to save data: ${error.substringAfter(": ")}"
                                error?.contains("Upload failed") == true -> "Upload failed: ${error.substringAfter(": ")}"
                                else -> "Error: ${error ?: "Unknown error"}"
                            }
                        }
                    }
                } else {
                    status = "Please fill all fields"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isUploading
        ) {
            Text("Submit")
        }

        if (isUploading) {
            CircularProgressIndicator()
        }

        Text(text = status)
    }
}