package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val darkColorScheme = darkColorScheme(
    primary = Brown,
    onPrimary = Color.White,

    secondary = Teal200,
    onSecondary = Color.Black,

    tertiary = Brown,
    onTertiary = Color.White,

    surface = DarkGrey,
    onSurface = Color.White,

    background = Black,
    onBackground = Color.White,

    onError = Beige
)

private val lightColorScheme = lightColorScheme(
    primary = LightGrey,
    onPrimary = Color.Black,

    secondary = Teal200,
    onSecondary = Color.Black,

    tertiary = WhiteGrey,
    onTertiary = Color.Black,

    surface = White,
    onSurface = Color.Black,

    background = WhiteGrey2,
    onBackground = Color.Black,

    onError = Beige
)

@Composable
fun fanControlTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
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