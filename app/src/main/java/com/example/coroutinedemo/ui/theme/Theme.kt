
package com.example.coroutinedemo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = SagePrimary,
    secondary = SageLight,
    background = SageBackground,
    surface = SageWhite,
    onPrimary = SageWhite,
    onSecondary = SageDark,
    onBackground = SageDark,
    onSurface = SageDark,
    error = SageError
)

@Composable
fun CoroutineDemoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}
