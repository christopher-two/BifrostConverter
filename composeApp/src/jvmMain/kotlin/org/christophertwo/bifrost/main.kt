package org.christophertwo.bifrost

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import bifrostconverter.composeapp.generated.resources.Res
import bifrostconverter.composeapp.generated.resources.icono
import org.christophertwo.bifrost.di.ViewmodelModule
import org.christophertwo.bifrost.ui.navigation.NavigationApp
import org.christophertwo.bifrost.ui.theme.BifrostTheme
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger

fun main() = application {
    startKoin {
        logger(PrintLogger(Level.DEBUG))
        modules(
            ViewmodelModule
        )
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Bifröst Converter",
        resizable = false,
        icon = painterResource(Res.drawable.icono),
        state = rememberWindowState(
            width = 600.dp,
            height = 800.dp
        )
    ) {
        BifrostTheme {
            NavigationApp()
        }
    }
}