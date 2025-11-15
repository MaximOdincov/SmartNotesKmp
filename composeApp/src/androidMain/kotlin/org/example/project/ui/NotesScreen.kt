import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.model.Note
import org.example.project.ui.NoteEvent
import org.example.project.ui.NoteViewModel
import org.example.project.ui.ScreenState

@Composable
fun NotesListScreen(
    vm: NoteViewModel,
    onOpenNote: (Note) -> Unit
) {
    val state by vm.state.collectAsState()
    val padding = 16.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(padding)
    ) {
        when (state) {
            ScreenState.Loading -> CircularProgressIndicator(color = Color.White, modifier = Modifier.align(Alignment.Center))
            is ScreenState.Content -> {
                val notes = (state as ScreenState.Content).notes
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(notes) { note ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onOpenNote(note) },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Box(
                                modifier = Modifier.padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    note.title.ifBlank { "Untitled" },
                                    color = Color(0xFF6200EE),
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
            is ScreenState.Error -> Text(
                "Error: ${(state as ScreenState.Error).errorMessage}",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
            ScreenState.Initial -> {}
        }

        FloatingActionButton(
            onClick = {
                val newNote = Note(
                    id = System.currentTimeMillis().toString(),
                    title = "",
                    content = ""
                )
                vm.sendEvent(NoteEvent.SelectNote(newNote))
                onOpenNote(newNote)
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            containerColor = Color(0xFF6200EE) // фиолетовый по Material
        ) {
            Text("+", color = Color.White, fontSize = 24.sp)
        }
    }
}


