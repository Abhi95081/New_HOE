import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.new_hoe.Supabase.ItemData
import com.example.new_hoe.R
import fetchItems

@Composable
fun HomePage() {
    val context = LocalContext.current
    var items by remember { mutableStateOf<List<ItemData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        // Fetch data from Supabase when the screen is first launched
        isLoading = true
        val fetchedItems = fetchItems() // Fetch the items from Supabase
        items = fetchedItems
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hostel Online Exchange", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        if (isLoading) {
            CircularProgressIndicator()  // Show loading spinner while fetching data
        } else if (items.isEmpty()) {
            Text("No items available.")
        } else {
            // Display the items in a scrollable list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    ItemCard(item = item) // Use a Card for better visual presentation
                }
            }
        }
    }
}

@Composable
fun ItemCard(item: ItemData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display Image
            if (item.image_uri.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(item.image_uri),
                    contentDescription = "Item Image",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(bottom = 8.dp)
                )
            } else {
                // Placeholder if image URI is missing
                Text("No Image", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Display Description
            Text(text = item.description ?: "No Description", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(8.dp))

            // Display Cost
            Text(text = "Cost: ₹${item.cost ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)

            // Row for WhatsApp Icon
            Row(
                horizontalArrangement = Arrangement.End, // Position the icon to the right
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    // Add your WhatsApp share logic here
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.chat), // Use the WhatsApp image from drawable
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}
