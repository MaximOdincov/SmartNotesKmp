package org.example.project.ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.di.appModule
import org.koin.core.context.GlobalContext.startKoin

fun main() = application {
    startKoin {
        modules(appModule)
    }
    val vm = DesktopKoinInjector.vm

    Window(
        onCloseRequest = ::exitApplication,
        title = "SmartNotesKmp",
    ) {
        DesktopNotesScreen(vm)
    }
}