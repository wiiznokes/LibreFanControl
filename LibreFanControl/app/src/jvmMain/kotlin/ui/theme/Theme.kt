package ui.theme

import Themes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun fanControlTheme(
    theme: Themes,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (theme) {
        Themes.system -> isSystemInDarkTheme()
        Themes.light -> false
        Themes.dark -> true
    }

    val colors = when {
        darkTheme -> darkColors
        else -> CustomColors()
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalShapes provides CustomShapes(),
        LocalSpaces provides CustomSpaces()
    ) {
        MaterialTheme(
            typography = typography,
            content = content
        )
    }
}

