package ui.screen.topBar.configuration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import configuration.ConfigurationModel
import model.item.BaseI.Companion.checkNameTaken
import ui.component.ListChoiceDefault
import ui.component.managerListChoice
import ui.component.managerNameTextField
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import ui.utils.Resources

val viewModel = ConfigurationVM()

@Composable
fun configuration() {
    if (viewModel.settings.configList.isNotEmpty()) {

        val setting = viewModel.settings

        val index = when (setting.configId.value) {
            null -> -1
            else -> setting.configList.indexOfFirst {
                it.id == setting.configId.value
            }
        }

        val text = remember(setting.configId) {
            when (setting.configId.value) {
                null -> mutableStateOf(Resources.getString("common/none"))
                else -> mutableStateOf(setting.configList[index].name)
            }
        }


        if (setting.configId.value != null) {
            IconButton(
                onClick = { viewModel.saveConfiguration(text.value, index, setting.configId.value!!) }
            ) {
                Icon(
                    painter = Resources.getIcon("topBar/save40"),
                    contentDescription = null,
                    tint = LocalColors.current.onMainTopBar
                )
            }
        }


        configurationListChoice(
            text = text,
            configList = setting.configList,
            currentId = setting.configId.value,
            currentIndex = index,
        )

    }
    addConfiguration()
}


@Composable
private fun configurationListChoice(
    text: MutableState<String>,
    currentId: String?,
    currentIndex: Int,
    configList: SnapshotStateList<ConfigurationModel>,
) {

    val minWidth = 150.dp
    managerListChoice(
        text = text.value,
        textContent = {
            managerNameTextField(
                ids = Pair(null, currentId),
                text = text,
                onValueChange = { value ->
                    checkNameTaken(
                        names = configList.map { config ->
                            config.name
                        },
                        name = value,
                        index = currentIndex
                    )
                },
                modifier = Modifier
                    .widthIn(minWidth, 200.dp)
                    .height(35.dp),
                placeholder = Resources.getString("label/conf_name"),
                color = LocalColors.current.input,
                onColor = LocalColors.current.onInput,
                shape = MaterialTheme.shapes.large,
                enabled = currentId != null
            )
        },
        colors = ListChoiceDefault.listChoiceColors(
            base = LocalColors.current.mainTopBar,
            onBase = LocalColors.current.onMainTopBar,
            container = LocalColors.current.inputVariant,
            onContainer = LocalColors.current.onInputVariant
        ),
        baseModifier = Modifier,
        itemModifier = Modifier.size(width = minWidth, height = 35.dp),
        onItemClick = { viewModel.onChangeConfiguration(it) },
        size = 40,
        iconContent = { id, index ->
            Icon(
                modifier = Modifier
                    .clickable { viewModel.removeConfiguration(id, index) }
                    .padding(end = LocalSpaces.current.small),
                painter = Resources.getIcon("select/delete_forever24"),
                contentDescription = null,
                tint = LocalColors.current.onInputVariant
            )
        },
        ids = configList.map { it.id },
        names = configList.map { it.name }
    )
}