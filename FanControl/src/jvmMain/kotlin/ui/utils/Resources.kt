package ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.useResource
import org.json.JSONObject
import org.json.JSONTokener


private const val STRING_FILE_NAME = "strings.json"
private const val SUFFIX_DRAWABLE = "_FILL0_wght400_GRAD0_opsz48"

class Resources {

    companion object {
        private val _rootJsonObject: JSONObject

        init {
            val string = useResource("values/$STRING_FILE_NAME") {
                it.bufferedReader().readText()
            }
            _rootJsonObject = JSONTokener(string).nextValue() as JSONObject
        }

        fun getString(path: String): String {
            return utils.getString(
                path = path,
                rootJsonObject = _rootJsonObject
            )
        }

        @Composable
        fun getIcon(id: String): Painter {
            val density = LocalDensity.current // to calculate the intrinsic size of vector images (SVG, XML)
            return remember {
                useResource("drawable/$id$SUFFIX_DRAWABLE.svg") {
                    loadSvgPainter(it, density)
                }
            }
        }
    }
}