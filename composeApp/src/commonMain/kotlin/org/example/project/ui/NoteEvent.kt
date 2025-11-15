package org.example.project.ui

import org.example.project.model.Note

sealed class NoteEvent {
        data object LoadNotes : NoteEvent()
        data class AddNote(val note: Note) : NoteEvent()
        data class UpdateNote(val note: Note): NoteEvent()
        data class SelectNote(val note: Note): NoteEvent()
        data class DeleteNote(val note: Note): NoteEvent()
}