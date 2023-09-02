package settingSlidingWindows.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.useResource


@Composable
internal fun getIcon(id: String): Painter {
    val density = LocalDensity.current // to calculate the intrinsic size of vector images (SVG, XML)
    return remember {
        useResource("drawable/$id.svg") {
            loadSvgPainter(it, density)
        }
    }
}