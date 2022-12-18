package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
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
)

private val LightColorScheme = lightColorScheme(
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
)

@Composable
fun FanControlTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}