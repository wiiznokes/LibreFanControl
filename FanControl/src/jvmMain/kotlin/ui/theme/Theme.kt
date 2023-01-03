package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val darkColorScheme = darkColorScheme(
    // text field, floating button, list choice content
    primary = LightCyan,
    onPrimary = Color.Black,

    // main top bar background
    primaryContainer = DarkPurple,
    onPrimaryContainer = Color.White,

    // second top bar background
    secondaryContainer = LightPurple,
    onSecondaryContainer = Color.White,

    // add item choice
    secondary = Grey,
    onSecondary = Color.White,

    // conf choice text field, bottom navigation bar, conf dialog button
    tertiary = LightCyan,
    onTertiary = Color.Black,

    // background of the body
    background = Color.Black,
    onBackground = Color.White,

    // background of addItem
    inverseSurface = LightBlack,
    inverseOnSurface = Color.White,

    // background of item in body
    surface = BlueGrey,
    onSurface = Color.White,

    // background of item in add item, background of conf dialog
    surfaceVariant = MateGrey,
    onSurfaceVariant = Color.White,

    error = Beige
)

private val lightColorScheme = lightColorScheme(
    // text field, floating button, list choice content
    primary = Color.Magenta,
    onPrimary = Color.White,

    // main top bar background
    primaryContainer = Color.Blue,
    onPrimaryContainer = Color.White,

    // second top bar background
    secondaryContainer = Color.Green,
    onSecondaryContainer = Color.White,

    // add item choice
    secondary = Color.White,
    onSecondary = Color.Black,

    // conf choice text field, bottom navigation bar, conf dialog button
    tertiary = Color.Cyan,
    onTertiary = Color.Black,

    // background of the body
    background = Color.Black,
    onBackground = Color.White,

    // background of addItem
    inverseSurface = Color.DarkGray,
    inverseOnSurface = Color.White,

    // background of item in body
    surface = Color.Gray,
    onSurface = Color.White,

    // background of item in add item, background of conf dialog
    surfaceVariant = Color.LightGray,
    onSurfaceVariant = Color.White,

    error = Beige
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