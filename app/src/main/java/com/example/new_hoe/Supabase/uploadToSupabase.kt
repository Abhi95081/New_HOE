package com.example.new_hoe.Supabase

import android.content.Context
import android.net.Uri
import android.util.Log
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

private const val TAG = "SupabaseUpload"

suspend fun uploadToSupabase(
    context: Context,
    description: String,
    cost: String,
    uri: Uri
): Result<Unit> = withContext(Dispatchers.IO) {
    try {
        // Generate filename
        val fileName = "IMG_${System.currentTimeMillis()}_${(1000..9999).random()}.jpg"
        Log.d(TAG, "Filename: $fileName")

        // Get input stream
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: return@withContext Result.failure(IOException("Could not open URI stream").also {
                Log.e(TAG, "URI failure: $uri", it)
            })

        // Read bytes
        val bytes = try {
            inputStream.use { it.readBytes() }
        } catch (e: Exception) {
            return@withContext Result.failure(IOException("Failed to read image: ${e.message}", e).also {
                Log.e(TAG, "Read failure", it)
            })
        }

        if (bytes.isEmpty()) {
            return@withContext Result.failure(IOException("File is empty").also {
                Log.e(TAG, "Empty file")
            })
        }

        // Check size (5MB)
        if (bytes.size > 5 * 1024 * 1024) {
            return@withContext Result.failure(IOException("Image too large (max 5MB)").also {
                Log.e(TAG, "Size: ${bytes.size} bytes")
            })
        }

        // Upload and insert
        val storage = SupabaseClient.supabase.storage.from("items")
        try {
            Log.d(TAG, "Uploading to items bucket")
            storage.upload(fileName, bytes, upsert = true)
            val publicUrl = storage.publicUrl(fileName)
            Log.d(TAG, "Uploaded, URL: $publicUrl")

            try {
                val itemId = UUID.randomUUID().toString()
                Log.d(TAG, "Inserting: id=$itemId, image_url=$publicUrl, description=$description, cost=$cost")
                SupabaseClient.supabase.postgrest["items_data"].insert(
                    ItemData(
                        id = itemId,
                        description = description,
                        cost = cost,
                        image_uri = publicUrl
                    )
                )
                Log.d(TAG, "Insert successful")
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                // Clean up
                try {
                    Log.d(TAG, "Attempting to delete: $fileName")
                    storage.delete(fileName)
                    Log.d(TAG, "Deleted: $fileName")
                } catch (deleteError: Exception) {
                    Log.e(TAG, "Delete failed: $fileName", deleteError)
                }
                return@withContext Result.failure(IOException("Database insert failed: ${e.message}", e).also {
                    Log.e(TAG, "Insert failure", it)
                })
            }
        } catch (e: Exception) {
            return@withContext Result.failure(IOException("Storage upload failed: ${e.message}", e).also {
                Log.e(TAG, "Upload failure", it)
            })
        }
    } catch (e: Exception) {
        return@withContext Result.failure(IOException("Upload failed: ${e.message}", e).also {
            Log.e(TAG, "General failure", it)
        })
    }
}