package ui.popUp.exitApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.WindowPosition
import ui.component.managerButton
import ui.component.managerText
import ui.utils.Resources


val viewModel = ExitAppVM()


/**
 * @param onResult param : exitOnClose
 */
@Composable
fun exitApp(
    closeHasBeenClick: MutableState<Boolean>,
    onResult: (Boolean) -> Unit,
) {
    val settings = viewModel.settings.collectAsState().value

    val enabled = mutableStateOf(closeHasBeenClick.value && !settings.exitOnCloseSet)



    Dialog(
        state = DialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(width = 600.dp, height = 300.dp)),
        visible = enabled.value,
        resizable = false,
        onCloseRequest = {
            enabled.value = false
            closeHasBeenClick.value = false
        },
        title = Resources.getString("exit/title"),
        focusable = enabled.value
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseSurface),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val exitOnCloseSet = remember(closeHasBeenClick.value) { mutableStateOf(settings.exitOnCloseSet) }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                managerText(
                    text = Resources.getString("exit/remember_choice"),
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )

                Checkbox(
                    checked = exitOnCloseSet.value,
                    onCheckedChange = {
                        exitOnCloseSet.value = it
                    },
                    enabled = true,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                    )
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                managerButton(
                    onClick = {
                        viewModel.onButtonClick(
                            exitOnCloseSet = exitOnCloseSet.value,
                            exitOnClose = true
                        )
                        closeHasBeenClick.value = false
                        onResult(true)
                    },
                    text = Resources.getString("exit/exit"),
                )
                managerButton(
                    onClick = {
                        viewModel.onButtonClick(
                            exitOnCloseSet = exitOnCloseSet.value,
                            exitOnClose = false
                        )
                        closeHasBeenClick.value = false
                        onResult(false)
                    },
                    text = Resources.getString("exit/background"),
                )
            }
        }
    }
}


