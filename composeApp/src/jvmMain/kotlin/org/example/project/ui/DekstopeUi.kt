package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.model.Note

@Composable
fun DesktopNotesScreen(vm: NoteViewModel) {
    val state by vm.state.collectAsState()
    val selectedNote = vm.selectedNote

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // LEFT PANEL – список и кнопка добавления
        Column(
            modifier = Modifier
                .weight(0.35f)
                .fillMaxHeight()
                .padding(16.dp)
        ) {

            LazyColumn(modifier = Modifier.weight(1f)) {
                if (state is ScreenState.Content) {
                    val notes = (state as ScreenState.Content).notes
                    items(notes) { note ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { vm.sendEvent(NoteEvent.SelectNote(note)) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    note.title.ifBlank { "Untitled" },
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка Add
            Button(
                onClick = {
                    val newNote = Note(
                        id = System.currentTimeMillis().toString(),
                        title = "",
                        content = ""
                    )
                    vm.sendEvent(NoteEvent.SelectNote(newNote))
                },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Add Note")
            }
        }

        // RIGHT PANEL – редактор
        Box(
            modifier = Modifier
                .weight(0.65f)
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            if (selectedNote == null) {
                Text("Select a note", color = Color.White)
            } else {
                DesktopNoteEditor(vm)
            }
        }
    }
}

@Composable
private fun DesktopNoteEditor(vm: NoteViewModel) {
    val note = vm.selectedNote.value ?: return
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    Column(modifier = Modifier.fillMaxSize()) {
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
                .fillMaxSize()
                .padding(vertical = 8.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 18.sp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                if ((vm.state.value as? ScreenState.Content)?.notes?.any { it.id == note.id } == true) {
                    vm.sendEvent(NoteEvent.UpdateNote(note.copy(title = title, content = content)))
                } else {
                    vm.sendEvent(NoteEvent.AddNote(note.copy(title = title, content = content)))
                }
            }) {
                Text("Save")
            }

            Button(onClick = {
                vm.sendEvent(NoteEvent.DeleteNote(note))
            }) {
                Text("Delete")
            }
        }
    }
}

