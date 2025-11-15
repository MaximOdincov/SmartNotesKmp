package org.example.project.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.example.project.model.Note

class NoteApi(
    private val client: HttpClient,
    private val binId: String,
    private val apiKey: String
) {

    private val baseUrl = "https://api.jsonbin.io/v3/b/$binId"

    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    suspend fun getNotes(): List<Note> = withContext(Dispatchers.IO) {
        val response: HttpResponse = client.get(baseUrl) {
            header("X-Master-Key", apiKey)
        }
        val responseText = response.bodyAsText()
        val jsonElement = json.parseToJsonElement(responseText)
        val notesArray = jsonElement.jsonObject["record"]!!.jsonArray
        json.decodeFromString(notesArray.toString())
    }


    suspend fun saveNotes(notes: List<Note>) = withContext(Dispatchers.IO) {
        val bodyJson = json.encodeToString(notes)
        client.put(baseUrl) {
            header("X-Master-Key", apiKey)
            contentType(ContentType.Application.Json)
            setBody(bodyJson)
        }
    }

    suspend fun addNote(note: Note) {
        val currentNotes = getNotes().toMutableList()
        currentNotes.add(note)
        saveNotes(currentNotes)
    }

    suspend fun updateNote(note: Note){
        val currentNotes = getNotes().toMutableList()
        val index = currentNotes.indexOfFirst { it.id == note.id }
        if (index != -1) {
            currentNotes[index] = note
            saveNotes(currentNotes)
        }
    }

    suspend fun deleteNote(note:Note){
        val currentNotes = getNotes().toMutableList()
        val newList = currentNotes.filter { it.id != note.id }
        saveNotes(newList)
    }
}
