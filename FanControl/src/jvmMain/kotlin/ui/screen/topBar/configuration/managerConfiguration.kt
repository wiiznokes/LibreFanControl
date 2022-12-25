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
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import model.Configuration
import ui.component.managerListChoice
import ui.component.managerOutlinedTextField
import ui.component.managerText
import ui.component.managerTextField
import ui.screen.topBar.TopBarViewModel
import ui.utils.Resources
import ui.utils.getAvailableId

val viewModel = ConfigurationViewModel()

@Composable
fun managerConfiguration() {

    val currentConfig = viewModel.currentConfig.value

    if(currentConfig.value != null) {
        managerConfiguration(
            configId = currentConfig.value!!.id,
            configList = viewModel.configList.value,
            configName = currentConfig.value!!.name
        )
    }

    val dialogExpanded = remember { mutableStateOf(false) }

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
        visible = dialogExpanded
    )
}


@Composable
private fun managerConfiguration(
    configId: Long,
    configList: SnapshotStateList<Configuration>,
    configName: String
) {
    val expanded = remember { mutableStateOf(false) }


        IconButton(
            onClick = {
                viewModel.saveConfiguration(
                    configId,
                    configName
                )
            }
        ) {
            Icon(
                painter = Resources.getIcon("save_as"),
                contentDescription = Resources.getString("ct/save_conf")
            )
        }


        managerListChoice(
            textContent = {
                managerOutlinedTextField(
                    configName,
                    onValueChange = {
                        viewModel.onChangeName(
                            id = configId,
                            name = it
                        )
                    },
                    label = Resources.getString("label/conf_name"),
                    id = configId
                )
            },
            expanded = expanded
        ) {
            configList.forEach {
                DropdownMenuItem(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = {
                        viewModel.onChangeConfiguration(
                            id = it.id
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
                                it.id
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

@Composable
private fun managerDialogAddConfiguration(
    visible: MutableState<Boolean>,
    ) {



    val id = getAvailableId(
        list = viewModel.configList.value.map {
            it.id
        }
    )

    Dialog(
        visible = visible.value,
        onCloseRequest = {
            visible.value = false
        },
        title = Resources.getString("title/add_config")
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = mutableStateOf("")

            managerOutlinedTextField(
                text = text,
                onValueChange = {
                    viewModel.onChangeName(
                        name = it
                    )
                },
                label = Resources.getString("label/conf_name"),
                id = id
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Button(
                    onClick = {

                    }
                ){
                    managerText(
                        text = Resources.getString("cancel")
                    )
                }

                Button(
                    onClick = {

                    }
                ){
                    managerText(
                        text = Resources.getString("add")
                    )
                }
            }
        }
    }
}