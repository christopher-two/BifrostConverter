package org.christophertwo.bifrost.utils.routes

sealed class RoutesApp(val route: String) {
    object Calculator : RoutesApp("Calculator")
    object Start : RoutesApp("Start")
}