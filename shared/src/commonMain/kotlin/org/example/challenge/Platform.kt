package org.example.challenge

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform