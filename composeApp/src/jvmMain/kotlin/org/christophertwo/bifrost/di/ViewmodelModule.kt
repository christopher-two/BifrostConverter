package org.christophertwo.bifrost.di

import org.christophertwo.bifrost.ui.screens.calculator.CalculatorViewModel
import org.christophertwo.bifrost.ui.screens.start.StartViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewmodelModule: Module
    get() = module {
        viewModelOf(::StartViewModel)
        viewModelOf(::CalculatorViewModel)
    }