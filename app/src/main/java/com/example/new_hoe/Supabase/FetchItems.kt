import com.example.new_hoe.Supabase.ItemData
import com.example.new_hoe.Supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchItems(): List<ItemData> = withContext(Dispatchers.IO) {
    try {
        SupabaseClient.supabase
            .postgrest["items_data"]
            .select()
            .decodeList<ItemData>() // ✅ No .execute() needed
    } catch (e: RestException) {
        println("Supabase Error: ${e.message}")
        emptyList()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}
