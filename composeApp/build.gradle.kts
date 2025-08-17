import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {

            //Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.navigation.compose)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            //Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)

            //Utils
            implementation(libs.material.kolor)
            implementation(libs.composeIcons.fontAwesome)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.christophertwo.bifrost.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb) // Formatos de salida
            packageName = "bifrost"
            packageVersion = "1.0.0" // Versión de la app
            description = "App echa para estudiantes, para facilitar los sistemas numericos"
            copyright = "© 2025 Christopher Two. Todos los derechos reservados."
            vendor = "Christopher Two"

            windows {
                iconFile.set(project.file("src/main/resources/icono.ico"))
            }
            macOS {
                iconFile.set(project.file("src/main/resources/icono.icns"))
            }
            linux {
                iconFile.set(project.file("src/main/resources/icono.png"))
            }
        }
    }
}
