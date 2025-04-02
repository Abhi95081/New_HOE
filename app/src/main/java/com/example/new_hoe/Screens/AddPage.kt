package com.example.new_hoe.Screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import coil.compose.rememberAsyncImagePainter
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.ByteArrayOutputStream

@Composable
fun AddPage() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var userPrice by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Pick Image")
        }

        imageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Selected Image",
                modifier = Modifier.size(200.dp).padding(8.dp)
            )
        }

        if (description.isNotEmpty()) {
            Text(text = "Description: $description", modifier = Modifier.padding(8.dp))
        }

        if (price.isNotEmpty()) {
            Text(text = "AI Price: ₹$price", modifier = Modifier.padding(8.dp))
        }

        TextField(
            value = userPrice,
            onValueChange = { userPrice = it },
            label = { Text("Enter Your Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                imageUri?.let { uri ->
                    coroutineScope.launch(Dispatchers.IO) {
                        val (desc, estimatedPrice) = analyzeImage(uri, context)
                        description = desc
                        price = estimatedPrice
                    }
                }
            },
            enabled = imageUri != null
        ) {
            Text("Check Price")
        }
    }
}

// Function to Convert Image to Base64
fun encodeImageToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
}

// Function to Analyze Image using Hugging Face API
suspend fun analyzeImage(uri: Uri, context: Context): Pair<String, String> {
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    val imageBase64 = encodeImageToBase64(bitmap)
    return callHuggingFaceModel(imageBase64)
}

// Function to Call Hugging Face API
suspend fun callHuggingFaceModel(imageBase64: String): Pair<String, String> {
    val apiKey = "your_huggingface_api_key" // Replace with your actual API key
    val modelUrl = "https://api-inference.huggingface.co/models/paragon-AI/blip2-image-to-text"

    val httpClient = HttpClient(CIO)

    val response: HttpResponse = httpClient.post(modelUrl) {
        header("Authorization", "Bearer $apiKey")
        contentType(ContentType.Application.Json)
        setBody("""{"image": "$imageBase64"}""")
    }

    val jsonResponse = response.bodyAsText()
    val description = extractDescriptionFromResponse(jsonResponse)

    val estimatedPrice = estimatePrice(description)
    return Pair(description, estimatedPrice)
}

// Function to Extract Description from API Response
fun extractDescriptionFromResponse(response: String): String {
    val jsonObject = JSONObject(response)
    return jsonObject.getJSONArray("generated_text").getString(0)
}

// Dummy Price Estimation (Replace with actual pricing logic if needed)
fun estimatePrice(description: String): String {
    return when {
        description.contains("phone", ignoreCase = true) -> "₹10,000"
        description.contains("laptop", ignoreCase = true) -> "₹25,000"
        else -> "₹5,000"
    }
}
