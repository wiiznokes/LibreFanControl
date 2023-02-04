package ui.screen.topBar.configuration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import configuration.ConfigurationModel
import ui.component.ListChoiceDefault
import ui.component.managerListChoice
import ui.component.managerNameTextField
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import ui.utils.Resources
import utils.Name.Companion.checkNameTaken

val viewModel = ConfigurationVM()

@Composable
fun configuration() {
    if (viewModel.settings.value.configList.isNotEmpty()) {

        val setting = viewModel.settings.collectAsState().value

        val index = when (setting.configId) {
            null -> -1
            else -> setting.configList.indexOfFirst {
                it.id == setting.configId
            }
        }

        val text = remember(setting.configId) {
            when (setting.configId) {
                null -> mutableStateOf(Resources.getString("common/none"))
                else -> mutableStateOf(setting.configList[index].name)
            }
        }


        if (setting.configId != null) {
            IconButton(
                onClick = { viewModel.saveConfiguration(text.value, index, setting.configId!!) }
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
            currentId = setting.configId,
            currentIndex = index,
        )

    }
    addConfiguration()
}


@Composable
private fun configurationListChoice(
    text: MutableState<String>,
    currentId: Long?,
    currentIndex: Int,
    configList: SnapshotStateList<ConfigurationModel>
) {

    val minWidth = 150.dp
    managerListChoice(
        text = text.value,
        textContent = {
            managerNameTextField(
                value = it,
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