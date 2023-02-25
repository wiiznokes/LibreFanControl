package ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


val LocalColors = compositionLocalOf { CustomColors() }

val DarkPurple = Color(63, 59, 108)
val LightPurple = Color(129, 103, 151)
val BlueGrey = Color(43, 72, 101)
val LightOrange = Color(0xFF938BA1)

// default: Light theme
val LightBlue = Color(0xFFCFCFEA)
val LightBlue2 = Color(0xFF4FC3F7)


val Blue = Color(0xFF0D47A1)
val DarkBlue = Color(0xFF263238)
val LightGrey = Color(0xFFECEFF1)
val Grey = Color(0xFFB0BEC5)
val MateGrey = Color(0xFF546E7A)
val LightBlack = Color(0xFF212121)
val LightCyan = Color(0xFFE0F7FA)
val LightCyan2 =Color(163, 199, 214)
val Beige = Color(0xFFdfa9a9)

val OffWhite = Color(0xFFF2F2F2)

data class CustomColors(
    val input: Color = LightBlue,
    val onInput: Color = Color.Black,
    val inputVariant: Color = LightBlue2,
    val onInputVariant: Color = Color.Black,
    val inputMain: Color = Beige,
    val onInputMain: Color = Color.Black,
    val inactiveInput: Color = LightBlack,
    val onInactiveInput: Color = LightCyan,

    val error: Color = Beige,
    val onError: Color = Color.Black,

    val mainTopBar: Color = DarkBlue,
    val onMainTopBar: Color = Color.White,
    val secondTopBar: Color = Blue,
    val onSecondTopBar: Color = Color.White,
    val mainHeader: Color = Grey,
    val onMainHeader: Color = Color.White,
    val mainBackground: Color = LightGrey,
    val onMainBackground: Color = Color.Black,
    val mainContainer: Color = MateGrey,
    val onMainContainer: Color = Color.White,
    val mainSurface: Color = Color.White,
    val onMainSurface: Color = Color.Black,

    val secondHeader: Color = Grey,
    val onSecondHeader: Color = Color.White,
    val secondBackground: Color = OffWhite,
    val onSecondBackground: Color = LightBlack,
    val secondContainer: Color = MateGrey,
    val onSecondContainer: Color = Color.White,
    val secondSurface: Color = Color.White,
    val onSecondSurface: Color = Color.Black
)
val darkColors = CustomColors(

    input = LightCyan2,
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