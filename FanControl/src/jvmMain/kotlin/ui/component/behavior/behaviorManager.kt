package ui.component.behavior

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
import model.behavior.Behavior
import ui.MainViewModel
import ui.component.utils.managerOutlinedTextField
import ui.event.Event

@Composable
fun behavior(behavior: Behavior, viewModel: MainViewModel, index: Int) {


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

            Row {
                managerOutlinedTextField(
                    value = behavior.name,
                    label = "name",
                    onValueChange = {
                        viewModel.onEvent(
                            Event.Item.SetName(
                                name = it,
                                globalItemType = behavior.globalType,
                                index = index
                            )
                        )
                    }
                )
                Text(
                    text = behavior.specifyType
                )
            }

            Row {
                Text(
                    text = "Speed:"
                )
                Text(
                    text = "${behavior.value}%"
                )
            }
        }

    }
}



