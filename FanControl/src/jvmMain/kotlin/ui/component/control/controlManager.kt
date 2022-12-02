package ui.component.control

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.hardware.control.Control
import ui.MainViewModel
import ui.component.utils.managerOutlinedTextField
import ui.event.Event

@Composable
fun control(control: Control, viewModel: MainViewModel, index: Int) {


    Surface(
        modifier = Modifier
            .wrapContentSize(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {

        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {

            managerOutlinedTextField(
                value = control.name,
                label = "name",
                onValueChange = {
                    viewModel.onEvent(
                        Event.Item.SetName(
                            name = it,
                            globalItemType = control.globalType,
                            index = index
                        )
                    )
                }
            )

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Switch(
                    checked = !control.isAuto,
                    onCheckedChange = {
                        viewModel.onEvent(
                            Event.Item.Control.SetValue(
                                index = control.index,
                                isAuto = !control.isAuto,
                                value = control.value
                            )
                        )
                    }
                )
                Text(
                    text = control.behaviorId.toString()
                )
            }

            Row {
                Text(
                    text = "Speed:"
                )
                Text(
                    text = "${control.value}%"
                )
            }
        }

    }
}



