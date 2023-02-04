package ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp


data class CustomShapes(
    val small: RoundedCornerShape = RoundedCornerShape(2.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(5.dp),
    val large: RoundedCornerShape = RoundedCornerShape(10),

    val drawer: RoundedCornerShape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
)



val LocalShapes = compositionLocalOf { CustomShapes() }