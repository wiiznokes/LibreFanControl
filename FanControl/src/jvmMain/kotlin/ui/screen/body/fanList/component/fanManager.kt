package ui.screen.body.fanList.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.hardware.sensor.Fan
import ui.screen.body.fanList.FanViewModel
import ui.screen.component.managerOutlinedTextField
import ui.utils.ViewModelFactory

@Composable
fun fan(fan: Fan, index: Int) {

    val viewModel: FanViewModel

    if (ViewModelFactory.fanViewModel == null) {
        ViewModelFactory.fanViewModel = FanViewModel().also {
            viewModel = it
        }
    } else {
        viewModel = ViewModelFactory.fanViewModel!!
    }


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
                value = fan.name,
                label = "name",
                onValueChange = {

                    viewModel.setName(
                        name = it,
                        index = index
                    )
                }
            )


            Row {
                Text(
                    text = "value:"
                )

                Text(
                    text = "${fan.value} °C"
                )
            }
        }

    }
}

