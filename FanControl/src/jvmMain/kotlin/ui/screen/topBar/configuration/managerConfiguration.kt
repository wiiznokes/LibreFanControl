package ui.screen.topBar.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import model.Configuration
import ui.component.*
import ui.utils.Resources
import ui.utils.checkNameTaken
import ui.utils.getAvailableId

val viewModel = ConfigurationViewModel()

@Composable
fun managerConfiguration() {

    val index = viewModel.indexConfig.value
    val dialogExpanded = remember { mutableStateOf(false) }

    if (index != -1) {
        managerConfiguration(
            index = index,
            configList = viewModel.configList.value
        )
    }

    // add configuration
    IconButton(
        onClick = {
            dialogExpanded.value = true
        }
    ) {
        Icon(
            painter = Resources.getIcon("add"),
            contentDescription = Resources.getString("ct/add_conf")
        )
    }


    managerDialogAddConfiguration(
        enabled = dialogExpanded
    )
}


@Composable
private fun managerConfiguration(
    index: Int,
    configList: SnapshotStateList<Configuration>
) {
    val expanded = remember { mutableStateOf(false) }

    val text: MutableState<String> = remember(
        configList[index].id
    ) {
        mutableStateOf(configList[index].name)
    }


    IconButton(
        onClick = {
            viewModel.saveConfiguration(text.value)
        }
    ) {
        Icon(
            painter = Resources.getIcon("save_as"),
            contentDescription = Resources.getString("ct/save_conf")
        )
    }

    managerListChoice(
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
                        name = it
                    )
                },
                id = configList[index].id,
                text = text,
                placeholder = Resources.getString("label/conf_name")
            )
        },
        expanded = expanded
    ) {
        configList.forEachIndexed { index, _ ->
            DropdownMenuItem(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                onClick = {
                    viewModel.onChangeConfiguration(
                        index = index
                    )
                    expanded.value = false
                }
            ) {
                managerText(
                    text = Resources.getString("none")
                )
                IconButton(
                    onClick = {
                        viewModel.removeConfiguration(
                            index = index
                        )
                    }
                ) {
                    Icon(
                        painter = Resources.getIcon("delete_forever"),
                        contentDescription = Resources.getString("ct/remove_conf")
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun managerDialogAddConfiguration(
    enabled: MutableState<Boolean>,
) {

    val configList = viewModel.configList.value

    val id = getAvailableId(
        ids = configList.map {
            it.id
        }
    )

    Dialog(
        visible = enabled.value,
        onCloseRequest = {
            enabled.value = false
        },
        title = Resources.getString("title/add_config"),
        focusable = enabled.value,
        onPreviewKeyEvent = {
            when (it.key) {
                Key.Escape -> {
                    enabled.value = false
                    return@Dialog true
                }

                else -> {
                    return@Dialog false
                }
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surface
                ),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val text = remember(
                id
            ) {
                mutableStateOf("")
            }

            managerNameOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                onValueChange = {
                    checkNameTaken(
                        names = configList.map { config ->
                            config.name
                        },
                        name = it
                    )
                },
                label = Resources.getString("label/conf_name"),
                id = id,
                text = text
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Button(
                    onClick = {
                        enabled.value = false
                    }
                ) {
                    managerText(
                        text = Resources.getString("cancel")
                    )
                }

                Button(
                    onClick = {
                        if (viewModel.addConfiguration(
                                name = text.value,
                                id = id
                            )
                        ) {
                            enabled.value = false
                        }
                    }
                ) {
                    managerText(
                        text = Resources.getString("add")
                    )
                }
            }
        }
    }
}