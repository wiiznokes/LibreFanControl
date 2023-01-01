package ui.screen.topBar.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.ConfigurationModel
import ui.component.managerConfigNameRoundedTextField
import ui.component.managerListChoice
import ui.component.managerText
import ui.utils.Resources
import utils.checkNameTaken

val viewModel = ConfigurationViewModel()

@Composable
fun managerConfig() {
    if (viewModel.settings.value.configList.isNotEmpty()) {
        val id = viewModel.settings.value.configId
        if (id.value != null) {
            managerConfigWithId(
                id = id.value!!,
                configList = viewModel.settings.value.configList
            )
        } else {
            managerConfigListChoice(
                textContent = {
                    managerText(
                        text = it,
                        modifier = Modifier
                            .width(200.dp)
                            .height(35.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(22.dp), //rounded corners
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                configList = viewModel.settings.value.configList,
                enabled = !viewModel.controlChange.collectAsState().value
            )
        }
    }

    managerAddConfig()
}


@Composable
private fun managerConfigWithId(
    id: Long,
    configList: SnapshotStateList<ConfigurationModel>
) {
    val index = viewModel.settings.value.configList.indexOfFirst {
        it.id == viewModel.settings.value.configId.value
    }

    val text: MutableState<String> = remember(
        id
    ) {
        mutableStateOf(configList[index].name)
    }

    IconButton(
        onClick = {
            viewModel.saveConfiguration(text.value, index, id)
        }
    ) {
        Icon(
            painter = Resources.getIcon("save_as"),
            contentDescription = Resources.getString("ct/save_conf")
        )
    }

    Spacer(Modifier.width(5.dp))

    managerConfigListChoice(
        text = text.value,
        enabled = !viewModel.controlChange.collectAsState().value,
        textContent = {
            managerConfigNameRoundedTextField(
                modifier = Modifier
                    .widthIn(200.dp, 250.dp)
                    .height(35.dp),
                value = text.value,
                onValueChange = {
                    checkNameTaken(
                        names = configList.map { config ->
                            config.name
                        },
                        name = it,
                        index = index
                    )
                },
                id = id,
                text = text,
                placeholder = Resources.getString("label/conf_name")
            )
        },
        configList = viewModel.settings.value.configList
    )
}

@Composable
private fun managerConfigListChoice(
    text: String? = null,
    enabled: Boolean = true,
    textContent: @Composable (String) -> Unit,
    configList: SnapshotStateList<ConfigurationModel>
) {
    managerListChoice(
        text = text,
        textContent = textContent,
        enabled = enabled,
        baseModifier = Modifier,
        dropDownModifier = Modifier
            .width(180.dp),
        onItemClick = {
            viewModel.onChangeConfiguration(it)
        },
        iconContent = { id, index ->
            IconButton(
                onClick = { viewModel.removeConfiguration(id, index) }
            ) {
                Icon(
                    painter = Resources.getIcon("delete_forever"),
                    contentDescription = Resources.getString("ct/remove_conf")
                )
            }
        },
        ids = configList.map { it.id },
        names = configList.map { it.name }
    )
}