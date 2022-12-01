package ui.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.hardware.control.BaseControl
import model.hardware.control.FanControl
import ui.MainViewModel
import ui.event.Event


@Preview
@Composable
fun preview1()
{
    val viewModel = MainViewModel()

    val control1 = FanControl(
        index = 0,
        id = "jjj",
        libName = "control1",
        isAuto = true,
        value = 50
    )

    control(control1, viewModel)
}
@Composable
fun control(control: BaseControl, viewModel: MainViewModel, index: Int) {

    Box(
       modifier = Modifier
           .wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
        ) {
            Column {
                TextField(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    value = {
                        if (control.name?.isNotEmpty() == true)
                            control.name
                        else
                            control.libName
                    },
                    onValueChange = {
                        viewModel.onEvent(Event.Item.SetName(
                            item = control,
                            it
                        ))
                    },
                    enabled = true,
                    singleLine = true,
                    label =  Text(text = "fan"),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.surface
                    )

                )
            }
        }
    }
}