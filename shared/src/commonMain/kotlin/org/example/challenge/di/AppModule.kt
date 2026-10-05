package org.example.challenge.di

import org.example.challenge.data.remote.DatabaseSeeder
import org.example.challenge.data.repository.AuthRepositoryImpl
import org.example.challenge.data.repository.MatchRepositoryImpl
import org.example.challenge.data.repository.ReviewRepositoryImpl
import org.example.challenge.data.repository.UserRepositoryImpl
import org.example.challenge.domain.repository.AuthRepository
import org.example.challenge.domain.repository.MatchRepository
import org.example.challenge.domain.repository.ReviewRepository
import org.example.challenge.domain.repository.UserRepository
import org.example.challenge.ui.auth.AuthViewModel
import org.example.challenge.ui.home.HomeViewModel
import org.example.challenge.ui.search.SearchViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * Koin module for dependency injection.
 */
val appModule: Module = module {
    single { DatabaseSeeder() }
    single<AuthRepository> { AuthRepositoryImpl() }
    single<MatchRepository> { MatchRepositoryImpl() }
    single<ReviewRepository> { ReviewRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    factory { AuthViewModel(get()) }
    factory { HomeViewModel(get()) }
    factory { SearchViewModel(get()) }
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
