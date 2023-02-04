package ui.screen.topBar.configuration

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
                    .widthIn(200.dp, 250.dp)
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
            onBase = LocalColors.current.onMainTopBar
        ),
        baseModifier = Modifier,
        itemModifier = Modifier.width(160.dp),
        onItemClick = { viewModel.onChangeConfiguration(it) },
        size = 40,
        iconContent = { id, index ->
            IconButton(
                onClick = { viewModel.removeConfiguration(id, index) }
            ) {
                Icon(
                    painter = Resources.getIcon("select/delete_forever40"),
                    contentDescription = null,
                    tint = LocalColors.current.onInputVariant
                )
            }
        },
        ids = configList.map { it.id },
        names = configList.map { it.name }
    )
}