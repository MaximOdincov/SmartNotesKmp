package org.example.project.data.repository

import org.example.project.data.network.NoteApi
import org.example.project.model.Note

class NoteRepository(
    private val api: NoteApi
) {
    suspend fun getNotes() = api.getNotes()
    suspend fun addNote(note: Note) = api.addNote(note)
    suspend fun saveNotes(notes: List<Note>) = api.saveNotes(notes)
    suspend fun updateNote(note: Note) = api.updateNote(note)
    suspend fun deleteNote(note: Note) = api.deleteNote(note)
}