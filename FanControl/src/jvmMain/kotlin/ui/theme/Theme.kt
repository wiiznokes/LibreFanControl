package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ui.screen.drawer.secondView.Themes

private val darkColorScheme = darkColorScheme(

    // second top bar background
    secondaryContainer = LightPurple,
    onSecondaryContainer = Color.White,

    // background of addItem, background of setting
    inverseSurface = LightBlack,
    inverseOnSurface = Color.White,

    // background of item in add item, background of conf dialog
    surfaceVariant = MateGrey,
    onSurfaceVariant = Color.White
)

private val lightColorScheme = lightColorScheme()


@Composable
fun fanControlTheme(
    theme: Themes,
    content: @Composable () -> Unit
) {
    val darkTheme = when (theme) {
        Themes.system -> isSystemInDarkTheme()
        Themes.light -> false
        Themes.dark -> true
    }

    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}