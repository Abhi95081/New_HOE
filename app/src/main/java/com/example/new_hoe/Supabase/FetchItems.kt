import com.example.new_hoe.Supabase.ItemData
import com.example.new_hoe.Supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchItems(): List<ItemData> = withContext(Dispatchers.IO) {
    try {
        // Fetch items from Supabase
        val result: PostgrestResult = SupabaseClient.supabase
            .from("items_data") // Table name
            .select() // Select all columns (modern SDK syntax)
            .execute()

        // Decode the result into List<ItemData>
        result.decodeList<ItemData>()
    } catch (e: RestException) {
        println("Supabase Error: ${e.message}")
        emptyList()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}