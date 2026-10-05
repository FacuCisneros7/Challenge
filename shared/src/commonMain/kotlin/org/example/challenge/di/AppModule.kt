package org.example.challenge.di

import org.example.challenge.data.repository.AuthRepositoryImpl
import org.example.challenge.domain.repository.AuthRepository
import org.example.challenge.ui.auth.AuthViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * Koin module for dependency injection.
 */
val appModule: Module = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    factory { AuthViewModel(get()) }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    try {
        startKoin {
            appDeclaration()
            modules(appModule)
        }
    } catch (e: Exception) {
        // Koin already started
    }
}
