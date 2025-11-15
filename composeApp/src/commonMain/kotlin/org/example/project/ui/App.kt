package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.model.Note
import org.example.project.data.network.HttpClientFactory
import org.example.project.data.network.NoteApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("CoroutineCreationDuringComposition")
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val client = HttpClientFactory().createClient()
            val api = NoteApi(client, binId = "69174d01d0ea881f40e85452", apiKey = "\$2a\$10\$utQThea6vv.xZC5eQAmjV.sU5scJ9pN7TuD25XhLoTyBDegm6csSK")
            val result = CoroutineScope(Dispatchers.Default).launch{
                    api.addNote(Note(id = "6", title = "Тест", content = "Содержимое заметки"))
                    val notes = api.getNotes()
                withContext(Dispatchers.Main){
                    notes.toString()
                }
            }
        }
    }
}