package org.christophertwo.bifrost.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle

@Composable
fun BifrostTheme(
    content: @Composable () -> Unit
) {
    DynamicMaterialTheme(
        seedColor = Color(0XFF5a1559),
        isDark = true,
        content = content,
        style = PaletteStyle.Rainbow
    )
}