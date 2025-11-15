package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.project.model.Note

@Composable
fun NoteEditScreen(
    note: Note?,
    vm: NoteViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 22.sp)
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 8.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 18.sp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                val newNote = Note(
                    id = note?.id ?: System.currentTimeMillis().toString(),
                    title = title,
                    content = content
                )
                if (note == null) vm.sendEvent(NoteEvent.AddNote(newNote))
                else vm.sendEvent(NoteEvent.UpdateNote(newNote))
                onBack()
            }) {
                Text("Save")
            }

            if (note != null) {
                Button(onClick = {
                    vm.sendEvent(NoteEvent.DeleteNote(note))
                    onBack()
                }) {
                    Text("Delete")
                }
            }
        }
    }
}


