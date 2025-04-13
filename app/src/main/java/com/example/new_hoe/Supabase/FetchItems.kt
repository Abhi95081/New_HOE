import com.example.new_hoe.Supabase.ItemData
import com.example.new_hoe.Supabase.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.github.jan.supabase.postgrest.PostgrestResponse

// Function to fetch items from Supabase
suspend fun fetchItems(): List<ItemData> = withContext(Dispatchers.IO) {
    try {
        // Fetch items from Supabase using the correct method
        val response: PostgrestResponse<ItemData> = SupabaseClient.supabase
            .from("items_data") // Specify the table name
            .select("*") // Select all columns
            .execute() // Execute query

        // Check if there was no error and return the list of items
        if (response.error == null) {
            response.body ?: emptyList() // Return list or empty if no data
        } else {
            emptyList() // In case of an error, return an empty list
        }
    } catch (e: Exception) {
        // Handle any errors during the fetch process
        e.printStackTrace() // Log error for debugging
        emptyList() // Return an empty list in case of an exception
    }
}
