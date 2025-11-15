package org.example.project.data.network

import kotlinx.serialization.Serializable
import org.example.project.model.Note

@Serializable
data class RecordWrapper(val record: List<Note>)
