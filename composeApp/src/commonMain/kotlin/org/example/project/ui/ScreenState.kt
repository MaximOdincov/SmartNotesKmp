package org.example.project.ui

import org.example.project.model.Note
import smartnoteskmp.composeapp.generated.resources.Res

sealed class ScreenState {
    object Initial: ScreenState()

    object Loading: ScreenState()

    class Content(val notes: List<Note>): ScreenState()

    class Error(val errorMessage: String): ScreenState()
}