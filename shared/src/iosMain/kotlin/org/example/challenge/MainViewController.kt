package org.example.challenge

import androidx.compose.ui.window.ComposeUIViewController
import org.example.challenge.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}
