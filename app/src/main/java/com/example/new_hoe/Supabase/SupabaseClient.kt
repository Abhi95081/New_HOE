package com.example.new_hoe.Supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://exruowestvylyfypzkeq.supabase.co",

        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImV4cnVvd2VzdHZ5bHlmeXB6a2VxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ0NzMyMDgsImV4cCI6MjA2MDA0OTIwOH0.iRvSBbkT8JVZ2pjGJAdIlezx0ljCihICoEEJg2pEisA"

    ) {
        install(Storage)
        install(Postgrest)
    }
}
