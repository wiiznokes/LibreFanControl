package ui.screen.addItem.controlList

import androidx.compose.foundation.BorderStroke
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
import ui.screen.body.controlList.ControlViewModel
import ui.screen.component.managerOutlinedTextField

@Composable
fun addControl(control: Control, index: Int) {

    val viewModel = ControlViewModel()


    Surface(
        modifier = Modifier
            .wrapContentSize(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onSurface
        )
    ) {

        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {

            managerOutlinedTextField(
                value = control.name,
                label = "name",
                onValueChange = {

                    viewModel.setName(
                        name = it,
                        index = index
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



