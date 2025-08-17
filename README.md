# Bifrost Converter

## 1. Resumen del Proyecto

Bifrost Converter es una aplicación de escritorio multiplataforma (para Windows, macOS y Linux) que funciona como una calculadora y conversor de sistemas numéricos avanzado. Su propósito principal es demostrar un entendimiento profundo de los sistemas numéricos (Decimal, Binario, Hexadecimal, Octal) y una arquitectura de desarrollo moderna utilizando Kotlin Multiplatform (KMP).

El nombre "Bifrost" hace alusión al puente de la mitología nórdica que conecta dos mundos, simbolizando cómo la aplicación conecta diferentes sistemas numéricos a través de una lógica compartida y centralizada.

## 2. Objetivos y Temas Demostrados
Este proyecto está diseñado para exhibir las siguientes habilidades y conocimientos:

**Fundamentos de Ciencias de la Computación:**

*   **Sistemas Numéricos:** Dominio completo de las bases numéricas 10, 2, 16 y 8, incluyendo las reglas de conversión y validación de cada una.

*   **Operaciones Aritméticas de Bajo Nivel:** Comprensión de cómo se realizan las operaciones matemáticas (+, -, *, /) en diferentes bases.

**Arquitectura de Software Moderno:**

*   **Kotlin Multiplatform (KMP):** Habilidad para estructurar un proyecto donde la lógica de negocio crítica se escribe una sola vez en Kotlin y se comparte entre diferentes plataformas.

**Desarrollo de UI para Escritorio:**

*   **Interfaces de Usuario Reactivas:** Capacidad para construir una interfaz de usuario (UI) fluida y moderna para escritorio utilizando Compose for Desktop, que se actualiza en tiempo real.

## 3. Funcionalidades Clave
### 3.1. Conversión en Tiempo Real
La ventana principal de la aplicación presenta cuatro campos de texto, cada uno correspondiente a un sistema numérico:

*   **Decimal (Base 10):** Acepta dígitos del 0 al 9.

*   **Binario (Base 2):** Acepta solo los dígitos 0 y 1.

*   **Hexadecimal (Base 16):** Acepta dígitos del 0 al 9 y letras de la A a la F.

*   **Octal (Base 8):** Acepta dígitos del 0 al 7.

La funcionalidad clave es la sincronización bidireccional instantánea. Cuando el usuario escribe un número válido en cualquier de los campos, los otros tres se actualizan en tiempo real para mostrar su valor equivalente.

### 3.2. Operaciones Aritméticas por Base
La aplicación incluye una vista de calculadora que permite al usuario:

*   Seleccionar una base de operación (Decimal, Binario, Hexadecimal u Octal).

*   Introducir dos operandos en esa base.

*   Realizar una operación aritmética básica (+, -, *, /).

El cálculo se realiza directamente en la base seleccionada, y el resultado se muestra en la misma base.

## 4. Arquitectura Técnica

El proyecto está construido sobre una base de **Kotlin Multiplatform** y sigue una arquitectura **MVVM (Model-View-ViewModel)** moderna dentro del módulo `composeApp`, que se compila para la JVM (`jvmMain`).

La interacción entre los componentes es unidireccional: la UI notifica al ViewModel de eventos, el ViewModel procesa la lógica y actualiza su Estado, y la UI reacciona a los cambios en el Estado.

### 4.1. Componentes Principales

*   **UI / Vistas (Compose for Desktop):**
    *   Construida con **Compose for Desktop**, la interfaz es declarativa y reactiva.
    *   `CalculatorScreen.kt`: Es la pantalla principal que contiene la UI de la calculadora. Observa el estado del `CalculatorViewModel` y le envía eventos del usuario.
    *   `StartScreen.kt`: Pantalla de bienvenida de la aplicación.
    *   `components/`: Contiene elementos de UI reutilizables como `NumeralView.kt` (para mostrar los valores) y `CalculatorKeypad.kt` (el teclado numérico y de operaciones).

*   **ViewModel (`CalculatorViewModel.kt`):**
    *   Actúa como el cerebro de la pantalla de la calculadora. No contiene ninguna referencia a la UI de Compose.
    *   Mantiene el estado de la UI en un `StateFlow` a través de la clase `CalculatorState`.
    *   Centraliza toda la lógica de negocio: validación de entradas, cálculos aritméticos (usando `java.math.BigInteger` para precisión) y cambios de base numérica.
    *   Procesa las acciones del usuario definidas en la interfaz sellada `CalculatorEvent`.

*   **Estado (`CalculatorState.kt`):**
    *   Una `data class` inmutable que representa el estado completo de la UI de la calculadora en un momento dado (ej. `currentValue`, `currentSystem`, `operation`, etc.).
    *   Cuando el estado cambia, la UI de Compose se recompone automáticamente para reflejar los nuevos datos.

*   **Eventos (`CalculatorEvent.kt`):**
    *   Una `sealed interface` que define todas las posibles interacciones del usuario (ej. `DigitInput`, `OperationInput`, `Clear`, `Equals`). Esto crea un contrato claro entre la UI y el ViewModel.

*   **Modelos y Utilidades:**
    *   `utils/enums/`: Contiene enumeraciones como `NumeralSystem.kt` y `Operation.kt` para representar las bases numéricas y las operaciones de forma segura y legible.
    *   `utils/routes/`: Define las rutas de navegación de la aplicación.

*   **Navegación (`NavigationApp.kt`):**
    *   Utiliza la biblioteca `androidx.navigation.compose` para gestionar la transición entre las diferentes pantallas de la aplicación (`StartScreen` y `CalculatorScreen`).

*   **Inyección de Dependencias (`di/ViewmodelModule.kt`):**
    *   Se utiliza **Koin** para la inyección de dependencias, lo que facilita la creación y provisión de instancias de los ViewModels a las vistas correspondientes.

## 5. Cómo Ejecutar el Proyecto
Para ejecutar la aplicación en un entorno de desarrollo, sigue estos pasos:

1.  **Clona el repositorio:**
    ```bash
    git clone https://github.com/christopher-two/BifrostConverter.git
    cd BifrostConverter
    ```

2.  **Abre el proyecto en IntelliJ IDEA o Android Studio.**

3.  **Ejecuta la configuración de `desktopApp`:**
    *   Dentro del IDE, busca la configuración de ejecución `desktopApp`.
    *   Haz clic en el botón de "Run" (ejecutar).

Esto compilará y lanzará la aplicación de escritorio en tu sistema operativo.
