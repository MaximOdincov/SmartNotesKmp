package org.example.project.di

import org.koin.dsl.module
import org.example.project.data.network.HttpClientFactory
import org.example.project.data.network.NoteApi
import org.example.project.data.repository.NoteRepository
import org.example.project.ui.NoteViewModel

val appModule = module {
    single {
        HttpClientFactory().createClient()
    }

    single {
        NoteApi(
            client = get(),
            binId = "69174d01d0ea881f40e85452",
            apiKey = "\$2a\$10\$utQThea6vv.xZC5eQAmjV.sU5scJ9pN7TuD25XhLoTyBDegm6csSK"
        )
    }

    single { NoteRepository(get()) }

    single { NoteViewModel(get()) }
}