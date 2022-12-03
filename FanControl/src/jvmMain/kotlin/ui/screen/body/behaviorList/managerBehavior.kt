package ui.screen.body.behaviorList

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
import ui.screen.component.managerOutlinedTextField


@Composable
fun behavior(
    behavior: Behavior,
    index: Int
) {

    val viewModel = BehaviorViewModel()

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
                        viewModel.setName(
                            name = it,
                            index = index
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



