package ui.component


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ui.utils.Resources


@Composable
fun baseBehavior(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,


    value: Int = 50,
    onMoreButtonClick: (Int) -> Unit,
    onLessButtonClick: (Int) -> Unit,
    onSliderValueChange: (Int) -> Unit
) {
    baseItemBody(
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        name = name,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            managerText(Resources.getString("behavior_fan_speed"))

            Box {
                Row {


                    IconButton(
                        onClick = {
                            onLessButtonClick(value)
                        }
                    ) {
                        Icon(
                            painter = Resources.getIcon("remove"),
                            contentDescription = Resources.getString("decrease_button_content_description")
                        )
                    }
                    IconButton(
                        onClick = {
                            onMoreButtonClick(value)
                        }
                    ) {
                        Icon(
                            painter = Resources.getIcon("add"),
                            contentDescription = Resources.getString("increase_button_content_description")
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically,
        ) {

            Slider(
                value = value.toFloat() / 100,
                steps = 100,
                onValueChange = {
                    onSliderValueChange(
                        (it * 100).toInt()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.75f)
            )



            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
            managerText(
                text = "$value %"
            )
        }
    }
}