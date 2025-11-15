package org.example.project.ui

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DesktopKoinInjector : KoinComponent {
    val vm: NoteViewModel by inject()
}