package ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val Beige = Color(0xFFdfa9a9)

val DarkPurple = Color(63, 59, 108)
val LightPurple = Color(129, 103, 151)

val LightCyan = Color(163, 199, 214)

val BlueGrey = Color(43, 72, 101)

val LightBlack = Color(27, 36, 48)

val Grey = Color(81, 85, 126)

val MateGrey = Color(60, 64, 72)


/**
 * custom theme, I made it, and then test to remove
 * all custom colors, and it looks great.
 * I will keep it if future Material 3 update look bad.
 */
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

    // background of addItem, background of setting
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