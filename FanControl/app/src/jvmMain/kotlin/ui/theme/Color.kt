package ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


val LocalColors = compositionLocalOf { CustomColors() }

val Beige = Color(0xFFdfa9a9)
val DarkPurple = Color(63, 59, 108)
val LightPurple = Color(129, 103, 151)
val LightCyan = Color(163, 199, 214)
val BlueGrey = Color(43, 72, 101)
val LightBlack = Color(27, 36, 48)
val Grey = Color(81, 85, 126)
val MateGrey = Color(60, 64, 72)
val LightOrange = Color(0xFF938BA1)

// default: Light theme
data class CustomColors(

    val input: Color= LightCyan,
    val onInput: Color=Color.Black,
    val inputVariant: Color=LightCyan,
    val onInputVariant: Color=Color.Black,
    val inputMain: Color=Beige,
    val onInputMain: Color=Color.Black,
    val inactiveInput: Color=LightBlack,
    val onInactiveInput: Color=LightCyan,

    val error: Color=Beige,
    val onError: Color=Color.Black,


    val mainTopBar: Color=DarkPurple,
    val onMainTopBar: Color=Color.White,
    val secondTopBar: Color=LightPurple,
    val onSecondTopBar: Color=Color.White,


    val mainHeader: Color=Grey,
    val onMainHeader: Color=Color.White,
    val mainBackground: Color=Color.Black,
    val onMainBackground: Color=Color.White,
    val mainContainer: Color=BlueGrey,
    val onMainContainer: Color=Color.White,
    val mainSurface: Color=MateGrey,
    val onMainSurface: Color=Color.White,


    val secondHeader: Color=Grey,
    val onSecondHeader: Color=Color.White,
    val secondBackground: Color=LightBlack,
    val onSecondBackground: Color=Color.White,
    val secondContainer: Color=MateGrey,
    val onSecondContainer: Color=Color.White,
    val secondSurface: Color=Color.White,
    val onSecondSurface: Color=Color.White,
)

val darkColors = CustomColors(

    input = LightCyan,
    onInput = Color.Black,
    inputVariant = LightOrange,
    onInputVariant = Color.Black,
    inputMain = Beige,
    onInputMain = Color.Black,
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
    mainSurface = Color.Black,
    onMainSurface = Color.White,


    secondHeader = Grey,
    onSecondHeader = Color.White,
    secondBackground = LightBlack,
    onSecondBackground = Color.White,
    secondContainer = MateGrey,
    onSecondContainer = Color.White,
    secondSurface = Color.White,
    onSecondSurface = Color.White,
)