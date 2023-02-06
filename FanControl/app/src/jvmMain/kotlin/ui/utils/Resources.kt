package ui.utils

import State
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.useResource
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import org.json.JSONTokener


private const val PREFIX_STRING = "strings-"
private const val SUFFIX_STRING = ".json"

class Resources {

    companion object {
        private val _rootJsonObject: JSONObject

        private val language = State.settings.asStateFlow().value.language

        init {
            val string = useResource("values/$PREFIX_STRING$language$SUFFIX_STRING") {
                it.bufferedReader().readText()
            }
            _rootJsonObject = JSONTokener(string).nextValue() as JSONObject
        }

        fun getString(path: String): String {
            return utils.getJsonValue(
                path = path,
                obj = _rootJsonObject
            )!!
        }

        @Composable
        fun getIcon(id: String): Painter {
            val density = LocalDensity.current // to calculate the intrinsic size of vector images (SVG, XML)
            return remember {
                useResource("drawable/$id.svg") {
                    loadSvgPainter(it, density)
                }
            }
        }
    }
}