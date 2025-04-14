package com.example.new_hoe.Screens

import ItemCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import com.example.new_hoe.Supabase.ItemData
import fetchItems
import kotlinx.coroutines.launch


@Composable
fun SearchPage() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var allItems by remember { mutableStateOf<List<ItemData>>(emptyList()) }
    var filteredItems by remember { mutableStateOf<List<ItemData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        isLoading = true
        allItems = fetchItems()
        filteredItems = allItems
        isLoading = false
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                filteredItems = allItems.filter { item ->
                    item.description?.contains(searchQuery.text, ignoreCase = true) == true ||
                            item.cost?.contains(searchQuery.text, ignoreCase = true) == true
                }
            },
            label = { Text("Search items...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else if (filteredItems.isEmpty()) {
            Text("No matching items found.", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredItems) { item ->
                    ItemCard(item = item)
                }
            }
        }
    }
}
