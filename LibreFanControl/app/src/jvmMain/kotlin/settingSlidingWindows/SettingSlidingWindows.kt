package settingSlidingWindows

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp


internal class SettingException(msg: String) : Exception(msg)


/**
 * Defaults value of Setting
 */
object SettingDefaults {

    /**
     * Default colors value for Setting
     */
    @Composable
    fun settingColors(
        container: Color = MaterialTheme.colorScheme.surface,
        onContainer: Color = MaterialTheme.colorScheme.onSurface,
        background: Color = MaterialTheme.colorScheme.background,
        onBackground: Color = MaterialTheme.colorScheme.onBackground
    ): SettingColors = SettingColors(
        container = container,
        onContainer = onContainer,
        background = background,
        onBackground = onBackground
    )

    @OptIn(ExperimentalUnitApi::class)
    @Composable
    fun settingTextStyle(
        headerStyle: TextStyle = MaterialTheme.typography.titleLarge,
        groupStyle: TextStyle = MaterialTheme.typography.labelMedium,
        itemTitleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
        itemSubTitleStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(
            lineHeight = TextUnit(5f, TextUnitType.Sp),
        ),
        advanceItemHeaderStyle: TextStyle = MaterialTheme.typography.titleMedium,
    ): SettingTextStyle = SettingTextStyle(
        headerStyle = headerStyle,
        groupStyle = groupStyle,
        itemTitleStyle = itemTitleStyle,
        itemSubTitleStyle = itemSubTitleStyle,
        advanceItemHeaderStyle = advanceItemHeaderStyle
    )

    const val iconSize = 24
    val smallPadding = 5.dp
    val mediumPadding = 10.dp
    val largePadding = 15.dp
}


data class SettingColors(
    val container: Color,
    val onContainer: Color,
    val background: Color,
    val onBackground: Color
)

data class SettingTextStyle(
    val headerStyle: TextStyle,
    val groupStyle: TextStyle,
    val itemTitleStyle: TextStyle,
    val itemSubTitleStyle: TextStyle,
    val advanceItemHeaderStyle: TextStyle,
)


data class SettingValue(
    val isFirstView: Boolean = true,
    val advanceIndex: Int = 0,
    val scrollState: Int = 0,
)


class SettingState(
    initialValue: SettingValue,
) {

    internal val lazyListState = LazyListState(initialValue.scrollState)

    private val currentValue = mutableStateOf(initialValue)
    val isFirstView: Boolean
        get() = currentValue.value.isFirstView

    val advanceIndex: Int
        get() = currentValue.value.advanceIndex


    /**
     * close advance and return to the first one
     */
    fun close() {
        currentValue.value = currentValue.value.copy(
            isFirstView = true
        )
    }

    /**
     * display advance setting of the corresponding index
     * from [advanceIndex]. If there is no advance setting,
     * the first view will be shown
     */
    fun openAdvance(int: Int) {
        currentValue.value = currentValue.value.copy(
            advanceIndex = int,
            isFirstView = false
        )
    }

}


/**
 * remember [SettingState] for you.
 * @param initialValue Initial value of [SettingState]
 * @param key Key used in remember, to know when recalculate the value
 */
@Composable
fun rememberSettingState(
    initialValue: SettingValue = SettingValue(),
    key: Any = Unit,
): SettingState {
    return remember(key) {
        SettingState(initialValue)
    }
}


/**
 * Entry point of this library. Display a list of setting,
 * declared in content params. A DSL [SettingScope] is provided,
 * and provide premake function that you can use to construct
 * a beautiful setting sliding windows !
 * @param settingState State of setting, used to make custom items
 * @param settingColors Colors of setting
 * @param modifier Modifier of the LazyList where items are
 * displayed
 * @param settingTextStyle Text style for all text use in setting
 * @param content DSL (Domain Specific Language)
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Setting(
    settingState: SettingState = rememberSettingState(),
    settingColors: SettingColors = SettingDefaults.settingColors(),
    modifier: Modifier = Modifier
        .fillMaxSize(),
    settingTextStyle: SettingTextStyle = SettingDefaults.settingTextStyle(),
    content: SettingScope.() -> Unit,
) {

    val map: MutableMap<Int, (@Composable () -> Unit)?> = mutableMapOf()
    val list: MutableList<@Composable () -> Unit> = mutableListOf()


    val settingScopeImpl = SettingScopeImpl(
        map = map,
        list = list,
        settingState = settingState,
        _settingColors = settingColors,
        settingTextStyle = settingTextStyle
    )
    settingScopeImpl.content()

    val isFirstView = when (settingState.isFirstView) {
        true -> true
        false -> {
            if (map.size > settingState.advanceIndex) {
                map[settingState.advanceIndex] == null
            } else true
        }
    }

    AnimatedContent(
        targetState = isFirstView,
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = {
                    if (isFirstView) -it else it
                }
            ) with slideOutHorizontally(
                targetOffsetX = {
                    if (isFirstView) it else -it
                }
            )
        }
    ) {
        if (it) {
            baseFirstView(
                modifier = modifier
                    .background(settingColors.background),
                settingState = settingState,
                list = list
            )
        } else {
            val contentFun = map[settingState.advanceIndex]
            Box(modifier.background(settingColors.background)) {
                contentFun?.invoke()
            }
        }
    }
}


@Composable
private fun baseFirstView(
    modifier: Modifier = Modifier,
    settingState: SettingState = rememberSettingState(),
    list: MutableList<@Composable () -> Unit>,
) {
    LazyColumn(
        modifier = modifier,
        state = settingState.lazyListState
    ) {
        items(list) {
            it()
        }
        item {
            Spacer(Modifier.height(80.dp))
        }
    }
}