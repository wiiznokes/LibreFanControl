package ui.screen.body.fanList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import model.hardware.sensor.Fan
import ui.screen.component.managerOutlinedTextField
import ui.screen.component.managerTextView

@Composable
fun fan(
    fan: Fan,
    index: Int,
    editModeActivated: StateFlow<Boolean>
) {

    val viewModel = FanViewModel()

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
        Box {


            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {


                managerOutlinedTextField(
                    value = fan.name,
                    label = "name",
                    onValueChange = {

                        viewModel.setName(
                            name = it,
                            index = index
                        )
                    }
                )

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                Row {
                    managerTextView(
                        text = "value:"
                    )
                    managerTextView(
                        text = "${fan.value} rpm"
                    )
                }
            }
            if(editModeActivated.value) {
                Button(
                    onClick = {
                        viewModel.remove(index)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Text("remove")
                }
            }
        }
    }
}

