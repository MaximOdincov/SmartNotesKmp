package org.example.project.data.network

import io.ktor.client.*

expect class HttpClientFactory() {
    fun createClient(): HttpClient
}