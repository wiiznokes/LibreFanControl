package ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


val LocalColors = compositionLocalOf { lightColors }

val Beige = Color(0xFFdfa9a9)
val DarkPurple = Color(63, 59, 108)
val LightPurple = Color(129, 103, 151)
val LightCyan = Color(163, 199, 214)
val BlueGrey = Color(43, 72, 101)
val LightBlack = Color(27, 36, 48)
val Grey = Color(81, 85, 126)
val MateGrey = Color(60, 64, 72)
val LightOrange = Color(0xFF938BA1)

data class CustomColors(

    val input: Color,
    val onInput: Color,
    val inputVariant: Color,
    val onInputVariant: Color,
    val inactiveInput: Color,
    val onInactiveInput: Color,

    val error: Color,
    val onError: Color,


    val mainTopBar: Color,
    val onMainTopBar: Color,
    val secondTopBar: Color,
    val onSecondTopBar: Color,


    val mainHeader: Color,
    val onMainHeader: Color,
    val mainBackground: Color,
    val onMainBackground: Color,
    val mainContainer: Color,
    val onMainContainer: Color,


    val secondHeader: Color,
    val onSecondHeader: Color,
    val secondBackground: Color,
    val onSecondBackground: Color,
    val secondContainer: Color,
    val onSecondContainer: Color,
    val secondSurface: Color,
    val onSecondSurface: Color,
)

val darkColors = CustomColors(

    input = LightCyan,
    onInput = Color.Black,
    inputVariant = LightOrange,
    onInputVariant = Color.Black,
    inactiveInput = LightBlack,
    onInactiveInput = LightOrange,

    error = Beige,
    onError = Color.Black,

    mainTopBar = DarkPurple,
    onMainTopBar = Color.White,
    secondTopBar = LightPurple,
    onSecondTopBar = Color.White,


    mainHeader = Grey,
    onMainHeader = Color.White,
    mainBackground = Color.Black,
    onMainBackground = Color.White,
    mainContainer = BlueGrey,
    onMainContainer = Color.White,


    secondHeader = Grey,
    onSecondHeader = Color.White,
    secondBackground = LightBlack,
    onSecondBackground = Color.White,
    secondContainer = MateGrey,
    onSecondContainer = Color.White,
    secondSurface = Color.White,
    onSecondSurface = Color.White,
)


val lightColors = CustomColors(

    input = LightCyan,
    onInput = Color.Black,
    inputVariant = LightCyan,
    onInputVariant = Color.Black,
    inactiveInput = LightBlack,
    onInactiveInput = LightCyan,

    error = Beige,
    onError = Color.Black,

    mainTopBar = DarkPurple,
    onMainTopBar = Color.White,
    secondTopBar = LightPurple,
    onSecondTopBar = Color.White,


    mainHeader = Grey,
    onMainHeader = Color.White,
    mainBackground = Color.Black,
    onMainBackground = Color.White,
    mainContainer = BlueGrey,
    onMainContainer = Color.White,

    secondHeader = Grey,
    onSecondHeader = Color.White,
    secondBackground = LightBlack,
    onSecondBackground = Color.White,
    secondContainer = MateGrey,
    onSecondContainer = Color.White,
    secondSurface = Color.White,
    onSecondSurface = Color.White,
)