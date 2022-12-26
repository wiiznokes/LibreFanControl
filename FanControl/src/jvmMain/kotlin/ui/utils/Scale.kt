package ui.utils

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp


class Scale(
    private val _width: Dp,
    private val _height: Dp,

    private val scale: Float,

    private val measureX: Boolean = false,
    private val measureY: Boolean = false,

    private val errorFactor: Float
) {

    private var width: MutableState<Dp>? = null
    private var height: MutableState<Dp>? = null

    @Composable
    fun init() {
        width = remember {
            mutableStateOf(_width)
        }
        height = remember {
            mutableStateOf(_height)
        }
    }

    @Composable
    fun getModifier(hasMeasured: MutableState<Boolean>): Modifier {
        val density = LocalDensity.current


        return when (hasMeasured.value) {
            false -> {
                Modifier
                    .onGloballyPositioned { coordinates ->
                        if (measureX)
                            width!!.value = with(density) { coordinates.size.width.toDp() } * errorFactor

                        if (measureY)
                            height!!.value = with(density) { coordinates.size.height.toDp() } * errorFactor

                        hasMeasured.value = true
                    }
            }

            true -> {
                Modifier
                    .size(width = width!!.value * scale, height = height!!.value * scale)
                    .requiredSize(width = width!!.value, height = height!!.value)
                    .scale(scale = scale)
            }
        }
    }
}