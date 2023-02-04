package ui.screen.topBar.configuration

import androidx.compose.foundation.layout.Spacer
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
import ui.screen.topBar.topBarHeight
import ui.theme.LocalColors
import ui.utils.Resources
import utils.Name.Companion.checkNameTaken

val viewModel = ConfigurationVM()

@Composable
fun configuration() {
    if (viewModel.settings.value.configList.isNotEmpty()) {
        val id = viewModel.settings.collectAsState().value.configId
        if (id != null) {
            configurationWithId(
                id = id,
                configList = viewModel.settings.value.configList
            )
        } else {
            configurationListChoice(
                textContent = {
                    managerNameTextField(
                        value = it,
                        ids = Pair(null, null),
                        modifier = Modifier
                            .widthIn(200.dp, 250.dp)
                            .height(topBarHeight - 10.dp),
                        color = LocalColors.current.onMainTopBar,
                        onColor = LocalColors.current.onMainTopBar,
                        shape = MaterialTheme.shapes.large,
                        enabled = false
                    )
                },
                configList = viewModel.settings.value.configList
            )
        }
    }
    addConfiguration()
}


@Composable
private fun configurationWithId(
    id: Long,
    configList: SnapshotStateList<ConfigurationModel>
) {
    val index = viewModel.settings.value.configList.indexOfFirst {
        it.id == id
    }

    val text: MutableState<String> = remember(id) {
        mutableStateOf(configList[index].name)
    }

    IconButton(
        onClick = { viewModel.saveConfiguration(text.value, index, id) }
    ) {
        Icon(
            painter = Resources.getIcon("topBar/save40"),
            contentDescription = Resources.getString("ct/save_conf"),
            tint = LocalColors.current.onMainTopBar
        )
    }

    Spacer(Modifier.width(5.dp))

    configurationListChoice(
        text = text.value,

        textContent = {
            managerNameTextField(
                value = text.value,
                ids = Pair(null, id),
                text = text,
                onValueChange = {
                    checkNameTaken(
                        names = configList.map { config ->
                            config.name
                        },
                        name = it,
                        index = index
                    )
                },
                modifier = Modifier
                    .widthIn(200.dp, 250.dp)
                    .height(35.dp),
                placeholder = Resources.getString("label/conf_name"),
                color = LocalColors.current.input,
                onColor = LocalColors.current.onInput,
                shape = MaterialTheme.shapes.large,
                enabled = true
            )
        },
        configList = viewModel.settings.value.configList
    )
}

@Composable
private fun configurationListChoice(
    text: String? = null,
    enabled: Boolean = true,
    id: Long? = null,
    configList: SnapshotStateList<ConfigurationModel>
) {
    managerListChoice(
        text = text,
        textContent = {
            managerNameTextField(
                value = it,
                ids = Pair(null, id),
                text = text,
                onValueChange = {
                    checkNameTaken(
                        names = configList.map { config ->
                            config.name
                        },
                        name = it,
                        index = index
                    )
                },
                modifier = Modifier
                    .widthIn(200.dp, 250.dp)
                    .height(35.dp),
                placeholder = Resources.getString("label/conf_name"),
                color = LocalColors.current.input,
                onColor = LocalColors.current.onInput,
                shape = MaterialTheme.shapes.large,
                enabled = true
            )
        },
        colors = ListChoiceDefault.listChoiceColors(
            base = LocalColors.current.mainTopBar,
            onBase = LocalColors.current.onMainTopBar
        ),
        enabled = enabled,
        baseModifier = Modifier,
        itemModifier = Modifier.width(180.dp),
        onItemClick = { viewModel.onChangeConfiguration(it) },
        size = 48,
        iconContent = { id, index ->
            IconButton(
                onClick = { viewModel.removeConfiguration(id, index) }
            ) {
                Icon(
                    painter = Resources.getIcon("select/delete_forever40"),
                    contentDescription = Resources.getString("ct/remove_conf"),
                    tint = LocalColors.current.onInputVariant
                )
            }
        },
        ids = configList.map { it.id },
        names = configList.map { it.name }
    )
}