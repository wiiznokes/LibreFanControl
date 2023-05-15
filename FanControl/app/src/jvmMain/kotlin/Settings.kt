import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList


class Settings(
    language: Languages = Languages.en,
    confId: String? = null,
    confInfoList: List<ConfInfo> = listOf(),
    updateDelay: Int = 2,
    theme: Themes = Themes.system,
    firstStart: Boolean = true,
    launchAtStartUp: Boolean = false,
    degree: Boolean = true,
    valueDisableControl: Int = 2,
    versionInstalled: String = ""
) {
    val language: MutableState<Languages> = mutableStateOf(language)
    val confId: MutableState<String?> = mutableStateOf(confId)
    val confInfoList = confInfoList.toMutableStateList()
    val updateDelay: MutableState<Int> = mutableStateOf(updateDelay)
    val theme: MutableState<Themes> = mutableStateOf(theme)
    val firstStart = mutableStateOf(firstStart)
    val launchAtStartUp = mutableStateOf(launchAtStartUp)
    val degree = mutableStateOf(degree)
    val valueDisableControl = mutableStateOf(valueDisableControl)
    val versionInstalled = mutableStateOf(versionInstalled)

    fun getIndexInfo(_confId: String? = confId.value) = when (_confId) {
        null -> null
        else -> confInfoList.indexOfFirst {
            it.id == confId.value
        }
    }


    fun getConfName(_confId: String? = confId.value): String? {
        return when (val index = getIndexInfo(_confId)) {
            null -> return null
            else -> confInfoList[index].name.value
        }
    }
}


class ConfInfo(
    val id: String,
    name: String,
) {
    var name = mutableStateOf(name)
}


// name need to respect the format in
// json string file (for now)

@Suppress("EnumEntryName")
enum class Languages {
    en,
    fr
}

@Suppress("EnumEntryName")
enum class Themes {
    system,
    light,
    dark
}