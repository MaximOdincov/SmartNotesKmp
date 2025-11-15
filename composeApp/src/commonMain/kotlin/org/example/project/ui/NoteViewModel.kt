package org.example.project.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.project.model.Note
import org.example.project.data.repository.NoteRepository
import org.example.project.ui.ScreenState.*

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state: StateFlow<ScreenState> = _state

    private val events = MutableSharedFlow<NoteEvent>()

     val selectedNote = mutableStateOf<Note?>(null)

    init {
        viewModelScope.launch {
            events.collect { event -> handleEvent(event) }
        }
        sendEvent(NoteEvent.LoadNotes)
    }

    fun sendEvent(event: NoteEvent) {
        viewModelScope.launch {
            events.emit(event)
        }
    }


    private suspend fun handleEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.LoadNotes -> {
                _state.value = ScreenState.Loading
                try {
                    val notes = repository.getNotes()
                    _state.value = Content(notes = notes)
                } catch (e: Exception) {
                    _state.value = Error(e.message.toString())
                }
            }

            is NoteEvent.AddNote -> {
                _state.value = ScreenState.Loading
                try {
                    repository.addNote(event.note)
                    val notes = repository.getNotes()
                    _state.value = Content(notes = notes)
                } catch (e: Exception) {
                    _state.value = Error(e.message.toString())
                }
            }

            is NoteEvent.UpdateNote -> {
                repository.updateNote(event.note)
                addReload()
            }

            is NoteEvent.DeleteNote -> {
                repository.deleteNote(event.note)
                addReload()
            }

            is NoteEvent.SelectNote -> {
                selectedNote.value = event.note
            }
        }
    }

    private suspend fun addReload() {
        val notes = repository.getNotes()
        _state.value = ScreenState.Content(notes)
    }
}